import { Heading, Link, Spacing } from '@fun-eat/design-system';
import { Suspense } from 'react';
import { Link as RouterLink } from 'react-router-dom';
import styled from 'styled-components';

import { CategoryMenu, SvgIcon, ScrollButton, Loading, ErrorBoundary, ErrorComponent } from '@/components/Common';
import { PBProductList, ProductList } from '@/components/Product';
import { ProductRankingList, ReviewRankingList } from '@/components/Rank';
import { PATH } from '@/constants/path';
import channelTalk from '@/service/channelTalk';

const HomePage = () => {
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
        <CategoryMenu menuVariant="food" />
        <Spacing size={12} />
        <ErrorBoundary fallback={ErrorComponent}>
          <Suspense fallback={<Loading />}>
            <ProductList category="food" isHomePage />
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
        <CategoryMenu menuVariant="store" />
        <Spacing size={16} />
        <ErrorBoundary fallback={ErrorComponent}>
          <Suspense fallback={<Loading />}>
            <PBProductList isHomePage />
          </Suspense>
        </ErrorBoundary>
      </section>
      <Spacing size={36} />
      <section>
        <Heading as="h2" size="xl">
          👑 랭킹
        </Heading>
        <Spacing size={12} />
        <ErrorBoundary fallback={ErrorComponent}>
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
        <ErrorBoundary fallback={ErrorComponent}>
          <Suspense fallback={<Loading />}>
            <ReviewRankingList isHomePage />
          </Suspense>
        </ErrorBoundary>
      </section>
      <ScrollButton />
    </>
  );
};

export default HomePage;

const ProductListRouteLink = styled(Link)`
  display: flex;
  align-items: center;
  justify-content: center;
  width: 100%;
  padding: 12px 0;
  text-align: center;
  border-bottom: 1px solid ${({ theme }) => theme.borderColors.disabled};

  svg {
    margin-left: 4px;
    transform: rotate(180deg);
  }
`;
