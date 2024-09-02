import type { Theme, SxProps, Breakpoint } from '@mui/material/styles';

import CryptoJS from 'crypto-js';
import { useState,useEffect } from 'react';
import { useNavigate } from 'react-router-dom';

import Box from '@mui/material/Box';
import Alert from '@mui/material/Alert';
import { useTheme } from '@mui/material/styles';

import { Main } from './main';
import { layoutClasses } from '../classes';
import { NavMobile, NavDesktop } from './nav';
import { navData } from '../config-nav-dashboard';
import { _workspaces } from '../config-nav-workspace';
import { MenuButton } from '../components/menu-button';
import { LayoutSection } from '../core/layout-section';
import { HeaderSection } from '../core/header-section';
import { AccountPopover } from '../components/account-popover';

// ----------------------------------------------------------------------

export type DashboardLayoutProps = {
  sx?: SxProps<Theme>;
  children: React.ReactNode;
  header?: {
    sx?: SxProps<Theme>;
  };
};

export function DashboardLayout({ sx, children, header }: DashboardLayoutProps) {
  const theme = useTheme();

  const [navOpen, setNavOpen] = useState(false);

  const layoutQuery: Breakpoint = 'lg';
    const navigate = useNavigate();
    const [userType, setUserType] = useState(null);
    const [filteredNavData, setFilteredNavData] = useState(navData);
    useEffect(() => {
        const token = sessionStorage.getItem('token');
        if (!token) {
            navigate('/sign-in');
        } else {
            try {
                const key = '3MVG9YDQS5WtC11pJQHW.SoB.PVU_4MYcq4utEs5X4qwgM5lFn1C0yJWcNMxsSkISMENouprev80ZOzLizehQ';
                const parts = token.split('.');
                if (parts.length !== 2) {
                    navigate('/sign-in');
                };

                const receivedHmac = parts[0];
                const encodedData = parts[1];
                const dataBytes = CryptoJS.enc.Base64.parse(encodedData);
                const data = CryptoJS.enc.Utf8.stringify(dataBytes);

                const hash = CryptoJS.HmacSHA256(dataBytes, CryptoJS.enc.Utf8.parse(key));
                const computedHmac = CryptoJS.enc.Hex.stringify(hash);

                if (computedHmac !== receivedHmac) {
                    navigate('/sign-in');
                }

                const decodedData = data.split(':'); // Assuming format "userType:email:expiryTime"
                // eslint-disable-next-line @typescript-eslint/no-shadow
                const userType = parseInt(decodedData[0], 10);
                const userId = parseInt(decodedData[1], 10);
                const userExp = parseInt(decodedData[2], 10);

                const newNavData = filterNavData(navData, userType);
                setFilteredNavData(newNavData);

                // @ts-ignore
                setUserType(userType);

                sessionStorage.setItem('userType', String(userType));
                sessionStorage.setItem('userId', String(userId));
                sessionStorage.setItem('userExp', String(userExp));

                if (userType === 5) {
                    navigate('/sign-in');
                }

            } catch (error) {
                console.error('Decryption error:', error);
                navigate('/sign-in');
            }
        }
    }, [navigate]);

    // @ts-ignore
    const filterNavData = (data, userTypeVal) => {
        switch (userTypeVal) {
            case 1:
                return data;
            case 2:
                // @ts-ignore
                return data.filter(item => item.title !== 'User'); // Exclude 'User'
            case 3:
                // @ts-ignore
                return data.filter(item => item.title !== 'User' && item.title !== 'Customers' && item.title !== 'Invoice'); // Exclude 'User', 'Customers', 'Invoice'
            case 4:
                // @ts-ignore
                return data.filter(item => item.title !== 'User' && item.title !== 'Invoice'); // Exclude 'User' and 'Invoice'
            default:
                return data;
        }
    };

  return (
    <LayoutSection
      /** **************************************
       * Header
       *************************************** */
      headerSection={
        <HeaderSection
          layoutQuery={layoutQuery}
          slotProps={{
            container: {
              maxWidth: false,
              sx: { px: { [layoutQuery]: 5 } },
            },
          }}
          sx={header?.sx}
          slots={{
            topArea: (
              <Alert severity="info" sx={{ display: 'none', borderRadius: 0 }}>
                This is an info Alert.
              </Alert>
            ),
            leftArea: (
              <>
                <MenuButton
                  onClick={() => setNavOpen(true)}
                  sx={{
                    ml: -1,
                    [theme.breakpoints.up(layoutQuery)]: { display: 'none' },
                  }}
                />
                <NavMobile
                  data={filteredNavData}
                  open={navOpen}
                  onClose={() => setNavOpen(false)}
                  workspaces={_workspaces}
                  // @ts-ignore
                  userType={userType}
                />
              </>
            ),
            rightArea: (
              <Box gap={1} display="flex" alignItems="center">
                <AccountPopover/>
              </Box>
            ),
          }}
        />
      }
      /** **************************************
       * Sidebar
       *************************************** */
      sidebarSection={
          <NavDesktop data={filteredNavData} layoutQuery={layoutQuery} workspaces={_workspaces} />
      }
      /** **************************************
       * Footer
       *************************************** */
      footerSection={null}
      /** **************************************
       * Style
       *************************************** */
      cssVars={{
        '--layout-nav-vertical-width': '300px',
        '--layout-dashboard-content-pt': theme.spacing(1),
        '--layout-dashboard-content-pb': theme.spacing(8),
        '--layout-dashboard-content-px': theme.spacing(5),
      }}
      sx={{
        [`& .${layoutClasses.hasSidebar}`]: {
          [theme.breakpoints.up(layoutQuery)]: {
            pl: 'var(--layout-nav-vertical-width)',
          },
        },
        ...sx,
      }}
    >
      <Main>{children}</Main>
    </LayoutSection>
  );
}
