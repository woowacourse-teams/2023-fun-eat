import { Button, Heading, Spacing, Text } from '@fun-eat/design-system';
import type { MouseEventHandler } from 'react';
import { Suspense, useState } from 'react';
import styled from 'styled-components';

import { ErrorBoundary, ErrorComponent, Input, Loading, SvgIcon, TabMenu } from '@/components/Common';
import { RecommendList, SearchResultList } from '@/components/Search';
import { SEARCH_PAGE_TABS } from '@/constants';
import { useDebounce } from '@/hooks/common';
import { useSearch } from '@/hooks/search';

const SearchPage = () => {
  const { searchQuery, isSubmitted, handleSearchQuery, handleSearch } = useSearch();
  const [debouncedSearchQuery, setDebouncedSearchQuery] = useState(searchQuery || '');
  const [selectedTabMenu, setSelectedTabMenu] = useState<string>(SEARCH_PAGE_TABS[0]);

  const handleTabMenuSelect: MouseEventHandler<HTMLButtonElement> = (event) => {
    setSelectedTabMenu(event.currentTarget.value);
  };

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
      <TabMenu
        tabMenus={SEARCH_PAGE_TABS}
        selectedTabMenu={selectedTabMenu}
        handleTabMenuSelect={handleTabMenuSelect}
      />
      <SearchResultSection>
        {isSubmitted && debouncedSearchQuery ? (
          <ErrorBoundary fallback={ErrorComponent}>
            <Suspense fallback={<Loading />}>
              <Heading as="h2" size="lg" weight="regular">
                <Mark>&apos;{searchQuery}&apos;</Mark>에 대한 검색결과입니다.
              </Heading>
              <Spacing size={20} />
              {selectedTabMenu === SEARCH_PAGE_TABS[0] ? <SearchResultList searchQuery={debouncedSearchQuery} /> : null}
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
