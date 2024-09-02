import type { IconButtonProps } from '@mui/material/IconButton';

import { useState, useCallback } from 'react';

import Box from '@mui/material/Box';
import Button from '@mui/material/Button';
import Avatar from '@mui/material/Avatar';
import Popover from '@mui/material/Popover';
import Divider from '@mui/material/Divider';
import MenuList from '@mui/material/MenuList';
import Typography from '@mui/material/Typography';
import IconButton from '@mui/material/IconButton';
import MenuItem, { menuItemClasses } from '@mui/material/MenuItem';

import { useRouter, usePathname } from 'src/routes/hooks';

import { _myAccount } from 'src/_mock';

// ----------------------------------------------------------------------

export type AccountPopoverProps = IconButtonProps & {
    data?: {
        label: string;
        href: string;
        icon?: React.ReactNode;
        info?: React.ReactNode;
    }[];
};

export function AccountPopover({ data = [], sx, ...other }: AccountPopoverProps) {
    const router = useRouter();

    const pathname = usePathname();

    const [openPopover, setOpenPopover] = useState<HTMLButtonElement | null>(null);

    const handleOpenPopover = useCallback((event: React.MouseEvent<HTMLButtonElement>) => {
        setOpenPopover(event.currentTarget);
    }, []);

    const handleClosePopover = useCallback(() => {
        setOpenPopover(null);
    }, []);

    const handleClickItem = useCallback(
        (path: string) => {
            handleClosePopover();
            router.push(path);
        },
        [handleClosePopover, router]
    );

    const handleLogout = useCallback(() => {
        sessionStorage.removeItem('token'); // Remove the token from session storage
        handleClosePopover(); // Close the popover
        router.push('/sign-in'); // Redirect to sign-in page
    }, [handleClosePopover, router]);

    return (
        <>
            <IconButton
                onClick={handleOpenPopover}
                sx={{
                    p: '2px',
                    width: 40,
                    height: 40,
                    background: (theme) =>
                        `conic-gradient(${theme.vars.palette.primary.light}, ${theme.vars.palette.warning.light}, ${theme.vars.palette.primary.light})`,
                    ...sx,
                }}
                {...other}
            >
                <Avatar src={_myAccount.photoURL} alt={_myAccount.displayName} sx={{ width: 1, height: 1 }}>
                    {_myAccount.displayName.charAt(0).toUpperCase()}
                </Avatar>
            </IconButton>

            <Popover
                open={!!openPopover}
                anchorEl={openPopover}
                onClose={handleClosePopover}
                anchorOrigin={{ vertical: 'bottom', horizontal: 'right' }}
                transformOrigin={{ vertical: 'top', horizontal: 'right' }}
                slotProps={{
                    paper: {
                        sx: { width: 200 },
                    },
                }}
            >
                <Divider sx={{ borderStyle: 'dashed' }} />

                <Box sx={{ p: 1 }}>
                    <Button fullWidth color="error" size="medium" variant="text" onClick={handleLogout}>
                        Logout
                    </Button>
                </Box>
            </Popover>
        </>
    );
}
