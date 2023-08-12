import { Button, Heading, Spacing, Text } from '@fun-eat/design-system';
import { Suspense, useState } from 'react';
import styled from 'styled-components';

import { ErrorBoundary, ErrorComponent, Input, Loading, SvgIcon, TabMenu } from '@/components/Common';
import { RecommendList, SearchedList } from '@/components/Search';
import { useSearch } from '@/hooks/search';
import useDebounce from '@/hooks/useDebounce';

const SearchPage = () => {
  const { searchQuery, isSubmitted, handleSearchQuery, handleSearch } = useSearch();
  const [debouncedSearchQuery, setDebouncedSearchQuery] = useState(searchQuery || '');

  useDebounce(
    () => {
      setDebouncedSearchQuery(searchQuery);
    },
    200,
    [searchQuery]
  );

  return (
    <>
      <SearchSection>
        <form onSubmit={handleSearch}>
          <Input
            customWidth="100%"
            placeholder="상품 이름을 검색해보세요."
            rightIcon={
              <Button customHeight="36px" color="white">
                <SvgIcon variant="search" />
              </Button>
            }
            value={searchQuery}
            onChange={handleSearchQuery}
          />
        </form>
        {!isSubmitted && debouncedSearchQuery && (
          <RecommendWrapper>
            <ErrorBoundary fallback={ErrorComponent}>
              <Suspense fallback={<Loading />}>
                <RecommendList searchQuery={debouncedSearchQuery} />
              </Suspense>
            </ErrorBoundary>
          </RecommendWrapper>
        )}
      </SearchSection>
      <Spacing size={20} />
      <TabMenu tabMenus={['상품', '꿀조합']} />
      <SearchResultSection>
        {isSubmitted && debouncedSearchQuery ? (
          <ErrorBoundary fallback={ErrorComponent}>
            <Suspense fallback={<Loading />}>
              <Heading as="h2" size="lg" weight="regular">
                <Mark>&apos;{searchQuery}&apos;</Mark>에 대한 검색결과입니다.
              </Heading>
              <Spacing size={20} />
              <SearchedList searchQuery={debouncedSearchQuery} />
            </Suspense>
          </ErrorBoundary>
        ) : (
          <Text>상품을 검색해보세요.</Text>
        )}
      </SearchResultSection>
    </>
  );
};

export default SearchPage;

const SearchSection = styled.section`
  position: relative;
`;

const RecommendWrapper = styled.div`
  position: absolute;
  top: 100%;
  left: 0;
  right: 0;
  height: fit-content;
  max-height: 150px;
  padding: 10px 0;
  background-color: ${({ theme }) => theme.backgroundColors.default};
  border: 1px solid ${({ theme }) => theme.borderColors.default};
  overflow-y: auto;
`;

const SearchResultSection = styled.section`
  margin-top: 30px;
`;

const Mark = styled.mark`
  background-color: ${({ theme }) => theme.backgroundColors.default};
  font-weight: ${({ theme }) => theme.fontWeights.bold};
`;
