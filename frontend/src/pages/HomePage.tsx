import { Heading, Spacing, Text, useTheme } from '@fun-eat/design-system';
import { useQueryErrorResetBoundary } from '@tanstack/react-query';
import { Suspense } from 'react';
import styled from 'styled-components';

import { Loading, ErrorBoundary, ErrorComponent, CategoryFoodList, CategoryStoreList } from '@/components/Common';
import { ProductRankingList, ReviewRankingList, RecipeRankingList } from '@/components/Rank';
import { IMAGE_URL } from '@/constants';
import channelTalk from '@/service/channelTalk';

export const HomePage = () => {
  const { reset } = useQueryErrorResetBoundary();
  const theme = useTheme();

  channelTalk.loadScript();

  channelTalk.boot({
    pluginKey: process.env.CHANNEL_TALK_KEY ?? '',
  });

  return (
    <>
      <section>
        <Banner src={`${IMAGE_URL}banner.png`} width={600} height={360} alt="이벤트 배너" />
      </section>
      <Spacing size={40} />
      <SectionWrapper>
        <Heading as="h2" size="xl">
          카테고리
        </Heading>
        <Spacing size={16} />
        <Suspense fallback={null}>
          <CategoryListWrapper>
            <CategoryFoodList />
            <CategoryStoreList />
          </CategoryListWrapper>
        </Suspense>
        <Spacing size={15} />
      </SectionWrapper>
      <Spacing size={40} />
      <SectionWrapper>
        <Heading as="h2" size="xl">
          🍯 꿀조합 랭킹
        </Heading>
        <Spacing size={8} />
        <Text size="sm" color={theme.textColors.info}>
          꿀조합 랭킹은 자체 알고리즘 기반으로 업데이트됩니다.
        </Text>
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
          🍙 상품 랭킹
        </Heading>
        <Spacing size={8} />
        <Text size="sm" color={theme.textColors.info}>
          상품 랭킹은 2주 단위로 업데이트됩니다.
        </Text>
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
        <Spacing size={8} />
        <Text size="sm" color={theme.textColors.info}>
          리뷰 랭킹은 자체 알고리즘 기반으로 업데이트됩니다.
        </Text>
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

const Banner = styled.img`
  width: 100%;
  height: auto;
`;

const SectionWrapper = styled.section`
  padding: 0 20px;
`;

const CategoryListWrapper = styled.div`
  overflow-x: auto;
  overflow-y: hidden;

  @media screen and (min-width: 500px) {
    display: flex;
    flex-direction: column;
    align-items: center;
  }

  &::-webkit-scrollbar {
    display: none;
  }
`;
