import { Button, Heading, Spacing, Text } from '@fun-eat/design-system';
import { useQueryErrorResetBoundary } from '@tanstack/react-query';
import { Suspense, useEffect, useState } from 'react';
import styled from 'styled-components';

import { ErrorBoundary, ErrorComponent, Input, Loading, SvgIcon, TabMenu } from '@/components/Common';
import { RecommendList, ProductSearchResultList, RecipeSearchResultList } from '@/components/Search';
import { SEARCH_TAB_VARIANTS } from '@/constants';
import { useDebounce, useTabMenu } from '@/hooks/common';
import { useSearch } from '@/hooks/search';

const PRODUCT_PLACEHOLDER = '상품 이름을 검색해보세요.';
const RECIPE_PLACEHOLDER = '꿀조합에 포함된 상품을 입력해보세요.';

const IntegratedSearchPage = () => {
  const {
    inputRef,
    searchQuery,
    isSubmitted,
    isAutocompleteOpen,
    handleSearchQuery,
    handleSearch,
    handleSearchClick,
    handleAutocompleteClose,
  } = useSearch();
  const [debouncedSearchQuery, setDebouncedSearchQuery] = useState(searchQuery || '');
  const { reset } = useQueryErrorResetBoundary();

  const { selectedTabMenu, isFirstTabMenu: isProductSearchTab, handleTabMenuClick } = useTabMenu();
  const inputPlaceholder = isProductSearchTab ? PRODUCT_PLACEHOLDER : RECIPE_PLACEHOLDER;

  useDebounce(
    () => {
      setDebouncedSearchQuery(searchQuery);
    },
    200,
    [searchQuery]
  );

  useEffect(() => {
    if (inputRef.current) {
      inputRef.current.focus();
    }
  }, []);

  return (
    <>
      <SearchSection>
        <form onSubmit={handleSearch}>
          <Input
            customWidth="100%"
            placeholder={inputPlaceholder}
            rightIcon={
              <Button customHeight="36px" color="white">
                <SvgIcon variant="search" />
              </Button>
            }
            value={searchQuery}
            onChange={handleSearchQuery}
            ref={inputRef}
          />
        </form>
        {!isSubmitted && debouncedSearchQuery && isAutocompleteOpen && (
          <ErrorBoundary fallback={ErrorComponent} handleReset={reset}>
            <Suspense fallback={<Loading />}>
              <RecommendList
                searchQuery={debouncedSearchQuery}
                handleSearchClick={handleSearchClick}
                handleAutocompleteClose={handleAutocompleteClose}
              />
            </Suspense>
          </ErrorBoundary>
        )}
      </SearchSection>
      <Spacing size={20} />
      <TabMenu
        tabMenus={SEARCH_TAB_VARIANTS}
        selectedTabMenu={selectedTabMenu}
        handleTabMenuSelect={handleTabMenuClick}
      />
      <SearchResultSection>
        {isSubmitted && searchQuery ? (
          <>
            <Heading as="h2" size="lg" weight="regular">
              <Mark>&apos;{searchQuery}&apos;</Mark>에 대한 검색결과입니다.
            </Heading>
            <ErrorBoundary fallback={ErrorComponent}>
              <Suspense fallback={<Loading />}>
                <Spacing size={20} />
                {isProductSearchTab ? (
                  <ProductSearchResultList searchQuery={searchQuery} />
                ) : (
                  <RecipeSearchResultList searchQuery={searchQuery} />
                )}
              </Suspense>
            </ErrorBoundary>
          </>
        ) : (
          <Text>{SEARCH_TAB_VARIANTS[selectedTabMenu]}을 검색해보세요.</Text>
        )}
      </SearchResultSection>
    </>
  );
};

export default IntegratedSearchPage;

const SearchSection = styled.section`
  position: relative;
`;

const SearchResultSection = styled.section`
  margin-top: 30px;
`;

const Mark = styled.mark`
  font-weight: ${({ theme }) => theme.fontWeights.bold};
  background-color: ${({ theme }) => theme.backgroundColors.default};
`;
