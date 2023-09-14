import { FunEatProvider } from '@fun-eat/design-system';
import { QueryClient, QueryClientProvider } from '@tanstack/react-query';
import { ReactQueryDevtools } from '@tanstack/react-query-devtools';
import React from 'react';
import ReactDOM from 'react-dom/client';
import ReactGA from 'react-ga4';
import { RouterProvider } from 'react-router-dom';

import { SvgSprite } from './components/Common';
import { ENVIRONMENT } from './constants';
import router from './router';
import GlobalStyle from './styles/globalStyle';

const initializeReactGA = () => {
  if (process.env.NODE_ENV === 'development') return;
  if (ENVIRONMENT === 'dev') return;

  ReactGA.initialize(process.env.GOOGLE_ANALYTICS_ID as string);
};
initializeReactGA();

const main = async () => {
  if (process.env.NODE_ENV === 'development') {
    const { worker } = await import('./mocks/browser');
    await worker.start();
  }
};
await main();

export const queryClient = new QueryClient({
  defaultOptions: {
    queries: {
      retry: false,
    },
  },
});

const root = ReactDOM.createRoot(document.getElementById('root') as HTMLElement);
root.render(
  <React.StrictMode>
    <QueryClientProvider client={queryClient}>
      <FunEatProvider>
        <SvgSprite />
        <GlobalStyle />
        <RouterProvider router={router} />
      </FunEatProvider>
      <ReactQueryDevtools initialIsOpen={false} />
    </QueryClientProvider>
  </React.StrictMode>
);
