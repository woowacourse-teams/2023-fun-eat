import { createBrowserRouter } from 'react-router-dom';

import App from '@/App';
import { PATH } from '@/constants/path';
import CategoryProvider from '@/contexts/CategoryContext';
import HomePage from '@/pages/HomePage';
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
        path: PATH.PRODUCT_LIST,
        element: (
          <CategoryProvider>
            <ProductListPage />
          </CategoryProvider>
        ),
      },
      {
        path: `${PATH.PRODUCT_LIST}/:productId`,
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
]);

export default router;
