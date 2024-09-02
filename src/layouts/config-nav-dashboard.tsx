import { Label } from 'src/components/label';
import { SvgColor } from 'src/components/svg-color';

// ----------------------------------------------------------------------

const icon = (name: string) => (
  <SvgColor width="100%" height="100%" src={`/assets/icons/navbar/${name}.svg`} />
);

export const navData = [
  {
    title: 'Dashboard',
    path: '/',
    icon: icon('ic-analytics'),
  },
  {
    title: 'Customers',
    path: '/customer',
    icon: icon('ic-customer'),
  },
  {
    title: 'Product',
    path: '/products',
    icon: icon('ic-cart'),
  },
  {
    title: 'Invoice',
    path: '/blog',
    icon: icon('ic-invoice'),
  },
  {
    title: 'User',
    path: '/user',
    icon: icon('ic-user'),
  },
  {
    title: 'My Account',
    path: '/user/my-account',
    icon: icon('ic-account'),
  },
];
