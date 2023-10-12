import { Navigate, createBrowserRouter } from 'react-router-dom';

import App from './App';

import { AuthLayout } from '@/components/Layout';
import { PATH } from '@/constants/path';
import CategoryProvider from '@/contexts/CategoryContext';
import NotFoundPage from '@/pages/NotFoundPage';

const router = createBrowserRouter([
  {
    path: '/',
    element: (
      <AuthLayout>
        <App />
      </AuthLayout>
    ),
    errorElement: <Navigate to={PATH.LOGIN} replace />,
    children: [
      {
        path: `${PATH.RECIPE}/:recipeId`,
        async lazy() {
          const { RecipeDetailPage } = await import(
            /* webpackChunkName: "RecipeDetailPage" */ '@/pages/RecipeDetailPage'
          );
          return { Component: RecipeDetailPage };
        },
      },
      {
        path: PATH.MEMBER,
        async lazy() {
          const { MemberPage } = await import(/* webpackChunkName: "MemberPage" */ '@/pages/MemberPage');
          return { Component: MemberPage };
        },
      },
      {
        path: `${PATH.MEMBER}/modify`,
        async lazy() {
          const { MemberModifyPage } = await import(
            /* webpackChunkName: "MemberModifyPage" */ '@/pages/MemberModifyPage'
          );
          return { Component: MemberModifyPage };
        },
      },
      {
        path: `${PATH.MEMBER}/review`,
        async lazy() {
          const { MemberReviewPage } = await import(
            /* webpackChunkName: "MemberReviewPage" */ '@/pages/MemberReviewPage'
          );
          return { Component: MemberReviewPage };
        },
      },
      {
        path: `${PATH.MEMBER}/recipe`,
        async lazy() {
          const { MemberRecipePage } = await import(
            /* webpackChunkName: "MemberRecipePage" */ '@/pages/MemberRecipePage'
          );
          return { Component: MemberRecipePage };
        },
      },
    ],
  },
  {
    path: '/',
    element: (
      <CategoryProvider>
        <App />
      </CategoryProvider>
    ),
    errorElement: <NotFoundPage />,
    children: [
      {
        index: true,
        async lazy() {
          const { HomePage } = await import(/* webpackChunkName: "HomePage" */ '@/pages/HomePage');
          return { Component: HomePage };
        },
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
        async lazy() {
          const { LoginPage } = await import(/* webpackChunkName: "LoginPage" */ '@/pages/LoginPage');
          return { Component: LoginPage };
        },
      },
      {
        path: `${PATH.LOGIN}/:authProvider`,
        async lazy() {
          const { AuthPage } = await import(/* webpackChunkName: "AuthPage" */ '@/pages/AuthPage');
          return { Component: AuthPage };
        },
      },
    ],
  },
  {
    path: '/',
    element: (
      <CategoryProvider>
        <App layout="headerOnly" />
      </CategoryProvider>
    ),
    errorElement: <NotFoundPage />,
    children: [
      {
        path: `${PATH.PRODUCT_LIST}/:category/:productId`,
        async lazy() {
          const { ProductDetailPage } = await import(
            /* webpackChunkName: "ProductDetailPage" */ '@/pages/ProductDetailPage'
          );
          return { Component: ProductDetailPage };
        },
      },
    ],
  },
  {
    path: '/',
    element: (
      <CategoryProvider>
        <App layout="simpleHeader" />
      </CategoryProvider>
    ),
    errorElement: <NotFoundPage />,
    children: [
      {
        path: `${PATH.PRODUCT_LIST}/:category`,
        async lazy() {
          const { ProductListPage } = await import(/* webpackChunkName: "ProductListPage" */ '@/pages/ProductListPage');
          return { Component: ProductListPage };
        },
      },
      {
        path: PATH.RECIPE,
        async lazy() {
          const { RecipePage } = await import(/* webpackChunkName: "RecipePage" */ '@/pages/RecipePage');
          return { Component: RecipePage };
        },
      },
      {
        path: `${PATH.SEARCH}/integrated`,
        async lazy() {
          const { IntegratedSearchPage } = await import(
            /* webpackChunkName: "IntegratedSearchPage" */ '@/pages/IntegratedSearchPage'
          );
          return { Component: IntegratedSearchPage };
        },
      },
      {
        path: `${PATH.SEARCH}/:searchVariant`,
        async lazy() {
          const { SearchPage } = await import(/* webpackChunkName: "SearchPage" */ '@/pages/SearchPage');
          return { Component: SearchPage };
        },
      },
    ],
  },
]);

export default router;
