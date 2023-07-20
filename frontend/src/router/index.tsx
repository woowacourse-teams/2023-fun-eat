import { createBrowserRouter } from 'react-router-dom';

import App from '@/App';
import { PATH } from '@/constants/path';
import HomePage from '@/pages/HomePage';
import ProductListPage from '@/pages/ProductList';
import ProfilePage from '@/pages/ProfilePage';
import RecipePage from '@/pages/RecipePage';
import SearchPage from '@/pages/SearchPage';

const router = createBrowserRouter([
  {
    path: '/',
    element: <App />,
    children: [
      {
        index: true,
        element: <HomePage />,
      },
      {
        path: PATH.PRODUCT_LIST,
        element: <ProductListPage />,
      },
      {
        path: PATH.RECIPE,
        element: <RecipePage />,
      },
      {
        path: PATH.SEARCH,
        element: <SearchPage />,
      },
      {
        path: PATH.PROFILE,
        element: <ProfilePage />,
      },
    ],
  },
]);

export default router;
