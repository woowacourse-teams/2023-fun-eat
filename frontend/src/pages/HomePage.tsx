import { Heading, Link, Spacing } from '@fun-eat/design-system';
import { useQueryErrorResetBoundary } from '@tanstack/react-query';
import { Suspense } from 'react';
import { Link as RouterLink } from 'react-router-dom';
import styled from 'styled-components';

import { CategoryMenu, SvgIcon, Loading, ErrorBoundary, ErrorComponent } from '@/components/Common';
import { PBProductList } from '@/components/Product';
import { ProductRankingList, ReviewRankingList, RecipeRankingList } from '@/components/Rank';
import { PATH } from '@/constants/path';
import channelTalk from '@/service/channelTalk';

const HomePage = () => {
  const { reset } = useQueryErrorResetBoundary();

  channelTalk.loadScript();

  channelTalk.boot({
    pluginKey: process.env.CHANNEL_TALK_KEY ?? '',
  });

  return (
    <>
      <section>
        <Heading as="h2" size="xl">
          공통 상품
        </Heading>
        <Spacing size={16} />
        <Suspense fallback={null}>
          <CategoryMenu menuVariant="food" />
        </Suspense>
        <Spacing size={12} />
        <ErrorBoundary fallback={ErrorComponent} handleReset={reset}>
          <Suspense fallback={<Loading />}>
            {/* <ProductList category="food" isHomePage /> */}
            <ProductListRouteLink as={RouterLink} to={`${PATH.PRODUCT_LIST}/food`}>
              전체 보기 <SvgIcon variant="arrow" width={12} height={12} />
            </ProductListRouteLink>
          </Suspense>
        </ErrorBoundary>
      </section>
      <Spacing size={36} />
      <section>
        <Heading as="h2" size="xl">
          편의점 특산품
        </Heading>
        <Spacing size={16} />
        <Suspense fallback={null}>
          <CategoryMenu menuVariant="store" />
        </Suspense>
        <Spacing size={16} />
        <ErrorBoundary fallback={ErrorComponent} handleReset={reset}>
          <Suspense fallback={<Loading />}>
            <PBProductList isHomePage />
          </Suspense>
        </ErrorBoundary>
      </section>
      <Spacing size={40} />
      <section>
        <Heading as="h2" size="xl">
          🍯 꿀조합 랭킹
        </Heading>
        <ErrorBoundary fallback={ErrorComponent} handleReset={reset}>
          <Suspense fallback={<Loading />}>
            <RecipeRankingList />
          </Suspense>
        </ErrorBoundary>
      </section>
      <Spacing size={36} />
      <section>
        <Heading as="h2" size="xl">
          👑 상품 랭킹
        </Heading>
        <Spacing size={12} />
        <ErrorBoundary fallback={ErrorComponent} handleReset={reset}>
          <Suspense fallback={<Loading />}>
            <ProductRankingList isHomePage />
          </Suspense>
        </ErrorBoundary>
      </section>
      <Spacing size={36} />
      <section>
        <Heading as="h2" size="xl">
          리뷰 랭킹
        </Heading>
        <Spacing size={12} />
        <ErrorBoundary fallback={ErrorComponent} handleReset={reset}>
          <Suspense fallback={<Loading />}>
            <ReviewRankingList isHomePage />
          </Suspense>
        </ErrorBoundary>
      </section>
      <Spacing size={36} />
    </>
  );
};

export default HomePage;

const ProductListRouteLink = styled(Link)`
  display: flex;
  justify-content: center;
  align-items: center;
  width: 100%;
  padding: 12px 0;
  text-align: center;
  border-bottom: 1px solid ${({ theme }) => theme.borderColors.disabled};

  svg {
    margin-left: 4px;
    transform: rotate(180deg);
  }
`;
