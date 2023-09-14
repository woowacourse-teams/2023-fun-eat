import { Heading, Link, Spacing } from '@fun-eat/design-system';
import { useQueryErrorResetBoundary } from '@tanstack/react-query';
import { Suspense } from 'react';
import { Link as RouterLink } from 'react-router-dom';
import styled from 'styled-components';

import { ScrollButton, Loading, ErrorBoundary, ErrorComponent, CategoryList } from '@/components/Common';
import { ProductRankingList, ReviewRankingList, RecipeRankingList } from '@/components/Rank';
import { IMAGE_URL } from '@/constants';
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
        <Banner src={`${IMAGE_URL}banner.png`} width={600} alt="이벤트 배너" />
      </section>
      <Spacing size={40} />
      <section>
        <Heading as="h2" size="xl">
          카테고리
        </Heading>
        <Spacing size={16} />
        <Suspense fallback={null}>
          <CategoryList />
        </Suspense>
        <Spacing size={15} />
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
        <Spacing size={15} />
        <ErrorBoundary fallback={ErrorComponent} handleReset={reset}>
          <Suspense fallback={<Loading />}>
            <ProductRankingList isHomePage />
          </Suspense>
        </ErrorBoundary>
      </section>
      <Spacing size={36} />
      <section>
        <Heading as="h2" size="xl">
          📝 리뷰 랭킹
        </Heading>
        <Spacing size={15} />
        <ErrorBoundary fallback={ErrorComponent} handleReset={reset}>
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

const Banner = styled.img`
  width: 100%;
`;
