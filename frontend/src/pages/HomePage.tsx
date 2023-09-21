import { Heading, Link, Spacing } from '@fun-eat/design-system';
import { useQueryErrorResetBoundary } from '@tanstack/react-query';
import { Suspense } from 'react';
import styled from 'styled-components';

import { Loading, ErrorBoundary, ErrorComponent, CategoryList } from '@/components/Common';
import { ProductRankingList, ReviewRankingList, RecipeRankingList } from '@/components/Rank';
import { IMAGE_URL } from '@/constants';
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
        <Link href={'https://www.instagram.com/p/CxNKXhfyZdw/?igshid=MzRlODBiNWFlZA=='} isExternal>
          <Banner src={`${IMAGE_URL}banner.png`} width={600} height={360} loading="lazy" alt="이벤트 배너" />
        </Link>
      </section>
      <Spacing size={40} />
      <SectionWrapper>
        <Heading as="h2" size="xl">
          카테고리
        </Heading>
        <Spacing size={16} />
        <Suspense fallback={null}>
          <CategoryList menuVariant="FOOD" />
          <CategoryList menuVariant="STORE" />
        </Suspense>
        <Spacing size={15} />
      </SectionWrapper>
      <Spacing size={40} />
      <SectionWrapper>
        <Heading as="h2" size="xl">
          🍯 꿀조합 랭킹
        </Heading>
        <Spacing size={16} />
        <ErrorBoundary fallback={ErrorComponent} handleReset={reset}>
          <Suspense fallback={<Loading />}>
            <RecipeRankingList />
          </Suspense>
        </ErrorBoundary>
      </SectionWrapper>
      <Spacing size={36} />
      <SectionWrapper>
        <Heading as="h2" size="xl">
          👑 상품 랭킹
        </Heading>
        <Spacing size={15} />
        <ErrorBoundary fallback={ErrorComponent} handleReset={reset}>
          <Suspense fallback={<Loading />}>
            <ProductRankingList isHomePage />
          </Suspense>
        </ErrorBoundary>
      </SectionWrapper>
      <Spacing size={36} />
      <SectionWrapper>
        <Heading as="h2" size="xl">
          📝 리뷰 랭킹
        </Heading>
        <Spacing size={15} />
        <ErrorBoundary fallback={ErrorComponent} handleReset={reset}>
          <Suspense fallback={<Loading />}>
            <ReviewRankingList isHomePage />
          </Suspense>
        </ErrorBoundary>
      </SectionWrapper>
      <Spacing size={36} />
    </>
  );
};

export default HomePage;

const Banner = styled.img`
  width: 100%;
  height: auto;
`;

const SectionWrapper = styled.section`
  padding: 0 20px;
`;
