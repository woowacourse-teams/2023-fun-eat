import { Outlet } from 'react-router-dom';

import { AuthLayout, DefaultLayout } from '@/components/Layout';

interface AppProps {
  layout?: 'auth' | 'default';
}

const App = ({ layout = 'default' }: AppProps) => {
  if (layout === 'auth') {
    return (
      <AuthLayout>
        <Outlet />
      </AuthLayout>
    );
  }

  return (
    <DefaultLayout>
      <Outlet />
    </DefaultLayout>
  );
};
export default App;
