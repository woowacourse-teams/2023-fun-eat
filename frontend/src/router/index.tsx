import { createBrowserRouter } from 'react-router-dom';

import App from './App';

import { PATH } from '@/constants/path';
import CategoryProvider from '@/contexts/CategoryContext';
import HomePage from '@/pages/HomePage';
import LoginPage from '@/pages/LoginPage';
import ProductDetailPage from '@/pages/ProductDetailPage';
import ProductListPage from '@/pages/ProductListPage';
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
        element: (
          <CategoryProvider>
            <HomePage />
          </CategoryProvider>
        ),
      },
      {
        path: `${PATH.PRODUCT_LIST}/:category`,
        element: (
          <CategoryProvider>
            <ProductListPage />
          </CategoryProvider>
        ),
      },
      {
        path: `${PATH.PRODUCT_LIST}/:category/:productId`,
        element: <ProductDetailPage />,
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
  {
    path: '/',
    element: <App layout="auth" />,
    children: [
      {
        path: PATH.LOGIN,
        element: <LoginPage />,
      },
    ],
  },
]);

export default router;
