import { createBrowserRouter } from 'react-router-dom';

import App from './App';

import ErrorBoundary from '@/components/Common/ErrorBoundary/ErrorBoundary';
import { PATH } from '@/constants/path';
import CategoryProvider from '@/contexts/CategoryContext';
import AuthPage from '@/pages/AuthPage';
import HomePage from '@/pages/HomePage';
import LoginPage from '@/pages/LoginPage';
import NotFoundPage from '@/pages/NotFoundPage';
import ProductDetailPage from '@/pages/ProductDetailPage';
import ProductListPage from '@/pages/ProductListPage';
import ProfileModifyPage from '@/pages/ProfileModifyPage';
import ProfilePage from '@/pages/ProfilePage';
import ProfileRecipe from '@/pages/ProfileRecipe';
import ProfileReview from '@/pages/ProfileReview';
import RecipePage from '@/pages/RecipePage';
import SearchPage from '@/pages/SearchPage';

const router = createBrowserRouter([
  {
    path: '/',
    element: (
      <ErrorBoundary fallback={NotFoundPage}>
        <App />
      </ErrorBoundary>
    ),
    errorElement: <NotFoundPage />,
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
      {
        path: `${PATH.PROFILE}/modify`,
        element: <ProfileModifyPage />,
      },
      {
        path: `${PATH.PROFILE}/review`,
        element: <ProfileReview />,
      },
      {
        path: `${PATH.PROFILE}/recipe`,
        element: <ProfileRecipe />,
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
      {
        path: `${PATH.LOGIN}/:authProvider`,
        element: <AuthPage />,
      },
    ],
  },
  {
    path: '/',
    element: <App layout="detail" />,
    children: [
      {
        path: `${PATH.PRODUCT_LIST}/:category/:productId`,
        element: <ProductDetailPage />,
      },
    ],
  },
]);

export default router;
