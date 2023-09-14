import { createBrowserRouter } from 'react-router-dom';

import App from './App';

import { AuthLayout } from '@/components/Layout';
import { PATH } from '@/constants/path';
import CategoryProvider from '@/contexts/CategoryContext';
import AuthPage from '@/pages/AuthPage';
import HomePage from '@/pages/HomePage';
import IntegratedSearchPage from '@/pages/IntegratedSearchPage';
import LoginPage from '@/pages/LoginPage';
import MemberModifyPage from '@/pages/MemberModifyPage';
import MemberPage from '@/pages/MemberPage';
import MemberRecipePage from '@/pages/MemberRecipePage';
import MemberReviewPage from '@/pages/MemberReviewPage';
import NotFoundPage from '@/pages/NotFoundPage';
import ProductDetailPage from '@/pages/ProductDetailPage';
import ProductListPage from '@/pages/ProductListPage';
import RecipeDetailPage from '@/pages/RecipeDetailPage';
import RecipePage from '@/pages/RecipePage';
import SearchPage from '@/pages/SearchPage';

const router = createBrowserRouter([
  {
    path: '/',
    element: <App />,
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
        path: `${PATH.RECIPE}/:recipeId`,
        element: (
          <AuthLayout>
            <RecipeDetailPage />
          </AuthLayout>
        ),
      },
      {
        path: `${PATH.SEARCH}/integrated`,
        element: <IntegratedSearchPage />,
      },
      {
        path: PATH.MEMBER,
        element: (
          <AuthLayout>
            <MemberPage />
          </AuthLayout>
        ),
      },
      {
        path: `${PATH.MEMBER}/modify`,
        element: (
          <AuthLayout>
            <MemberModifyPage />
          </AuthLayout>
        ),
      },
      {
        path: `${PATH.MEMBER}/review`,
        element: (
          <AuthLayout>
            <MemberReviewPage />
          </AuthLayout>
        ),
      },
      {
        path: `${PATH.MEMBER}/recipe`,
        element: (
          <AuthLayout>
            <MemberRecipePage />
          </AuthLayout>
        ),
      },
    ],
  },
  {
    path: '/',
    element: <App layout="minimal" />,
    errorElement: <NotFoundPage />,
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
    element: <App layout="headerOnly" />,
    errorElement: <NotFoundPage />,
    children: [
      {
        path: `${PATH.PRODUCT_LIST}/:category/:productId`,
        element: <ProductDetailPage />,
      },
    ],
  },
  {
    path: '/',
    element: <App layout="simpleHeader" />,
    errorElement: <NotFoundPage />,
    children: [
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
        path: `${PATH.SEARCH}/:searchVariant`,
        element: <SearchPage />,
      },
    ],
  },
]);

export default router;
