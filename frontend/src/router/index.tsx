import { createBrowserRouter } from 'react-router-dom';

import App from './App';

import ErrorBoundary from '@/components/Common/ErrorBoundary/ErrorBoundary';
import { PATH } from '@/constants/path';
import CategoryProvider from '@/contexts/CategoryContext';
import AuthPage from '@/pages/AuthPage';
import HomePage from '@/pages/HomePage';
import LoginPage from '@/pages/LoginPage';
import MemberModifyPage from '@/pages/MemberModifyPage';
import MemberPage from '@/pages/MemberPage';
import MemberRecipePage from '@/pages/MemberRecipePage';
import MemberReviewPage from '@/pages/MemberReviewPage';
import NotFoundPage from '@/pages/NotFoundPage';
import ProductDetailPage from '@/pages/ProductDetailPage';
import ProductListPage from '@/pages/ProductListPage';
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
        path: PATH.MEMBER,
        element: <MemberPage />,
      },
      {
        path: `${PATH.MEMBER}/modify`,
        element: <MemberModifyPage />,
      },
      {
        path: `${PATH.MEMBER}/review`,
        element: <MemberReviewPage />,
      },
      {
        path: `${PATH.MEMBER}/recipe`,
        element: <MemberRecipePage />,
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
