import React from 'react';
import ReactDOM from 'react-dom/client';
import App from './App';
import { FunEatProvider } from '@fun-eat/design-system';

const main = async () => {
  if (process.env.NODE_ENV === 'development') {
    const { worker } = await import('./mocks/browser');
    await worker.start();
  }
};
await main();

const root = ReactDOM.createRoot(document.getElementById('root') as HTMLElement);
root.render(
  <React.StrictMode>
    <FunEatProvider>
      <App />
    </FunEatProvider>
  </React.StrictMode>
);
