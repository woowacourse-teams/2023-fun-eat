import { useQueryErrorResetBoundary } from '@tanstack/react-query';
import { Suspense } from 'react';
import { Outlet } from 'react-router-dom';

import { ErrorBoundary, ErrorComponent, Loading } from '@/components/Common';
import { AuthLayout, DefaultLayout, DetailLayout } from '@/components/Layout';

interface AppProps {
  layout?: 'auth' | 'detail' | 'default';
}

const App = ({ layout = 'default' }: AppProps) => {
  const { reset } = useQueryErrorResetBoundary();

  if (layout === 'auth') {
    return (
      <ErrorBoundary fallback={ErrorComponent} handleReset={reset}>
        <Suspense fallback={<Loading />}>
          <AuthLayout>
            <Outlet />
          </AuthLayout>
        </Suspense>
      </ErrorBoundary>
    );
  }

  if (layout === 'detail') {
    return (
      <ErrorBoundary fallback={ErrorComponent} handleReset={reset}>
        <Suspense fallback={<Loading />}>
          <DetailLayout>
            <Outlet />
          </DetailLayout>
        </Suspense>
      </ErrorBoundary>
    );
  }

  return (
    <ErrorBoundary fallback={ErrorComponent} handleReset={reset}>
      <Suspense fallback={<Loading />}>
        <DefaultLayout>
          <Outlet />
        </DefaultLayout>
      </Suspense>
    </ErrorBoundary>
  );
};
export default App;
