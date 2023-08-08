import { FunEatProvider } from '@fun-eat/design-system';
import React from 'react';
import ReactDOM from 'react-dom/client';
import { RouterProvider } from 'react-router-dom';

import { SvgSprite } from './components/Common';
import MemberProvider from './contexts/MemberContext';
import router from './router';
import GlobalStyle from './styles';

import { QueryClient, QueryClientProvider } from '@tanstack/react-query';
import { ReactQueryDevtools } from '@tanstack/react-query-devtools';

const main = async () => {
  if (process.env.NODE_ENV === 'development') {
    const { worker } = await import('./mocks/browser');
    await worker.start();
  }
};
await main();

export const queryClient = new QueryClient();

const root = ReactDOM.createRoot(document.getElementById('root') as HTMLElement);
root.render(
  <React.StrictMode>
    <QueryClientProvider client={queryClient}>
      <MemberProvider>
        <FunEatProvider>
          <SvgSprite />
          <GlobalStyle />
          <RouterProvider router={router} />
        </FunEatProvider>
      </MemberProvider>
      <ReactQueryDevtools initialIsOpen={false} />
    </QueryClientProvider>
  </React.StrictMode>
);
