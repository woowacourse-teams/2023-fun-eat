import { createBrowserRouter } from 'react-router-dom';

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
    errorElement: <NotFoundPage />,
    children: [
      {
        path: `${PATH.RECIPE}/:recipeId`,
        async lazy() {
          const { RecipeDetailPage } = await import('@/pages/RecipeDetailPage');
          return { Component: RecipeDetailPage };
        },
      },
      {
        path: PATH.MEMBER,
        async lazy() {
          const { MemberPage } = await import('@/pages/MemberPage');
          return { Component: MemberPage };
        },
      },
      {
        path: `${PATH.MEMBER}/modify`,
        async lazy() {
          const { MemberModifyPage } = await import('@/pages/MemberModifyPage');
          return { Component: MemberModifyPage };
        },
      },
      {
        path: `${PATH.MEMBER}/review`,
        async lazy() {
          const { MemberReviewPage } = await import('@/pages/MemberReviewPage');
          return { Component: MemberReviewPage };
        },
      },
      {
        path: `${PATH.MEMBER}/recipe`,
        async lazy() {
          const { MemberRecipePage } = await import('@/pages/MemberRecipePage');
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
          const { HomePage } = await import(/* webpackChunkName: "home" */ '@/pages/HomePage');
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
          const { LoginPage } = await import('@/pages/LoginPage');
          return { Component: LoginPage };
        },
      },
      {
        path: `${PATH.LOGIN}/:authProvider`,
        async lazy() {
          const { AuthPage } = await import('@/pages/AuthPage');
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
          const { ProductDetailPage } = await import('@/pages/ProductDetailPage');
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
          const { ProductListPage } = await import('@/pages/ProductListPage');
          return { Component: ProductListPage };
        },
      },
      {
        path: PATH.RECIPE,
        async lazy() {
          const { RecipePage } = await import('@/pages/RecipePage');
          return { Component: RecipePage };
        },
      },
      {
        path: `${PATH.SEARCH}/integrated`,
        async lazy() {
          const { IntegratedSearchPage } = await import('@/pages/IntegratedSearchPage');
          return { Component: IntegratedSearchPage };
        },
      },
      {
        path: `${PATH.SEARCH}/:searchVariant`,
        async lazy() {
          const { SearchPage } = await import('@/pages/SearchPage');
          return { Component: SearchPage };
        },
      },
    ],
  },
]);

export default router;
