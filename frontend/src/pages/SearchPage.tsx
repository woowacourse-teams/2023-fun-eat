import { Button, Heading, Spacing, Text, useTheme } from '@fun-eat/design-system';
import { useQueryErrorResetBoundary } from '@tanstack/react-query';
import { Suspense, useEffect, useState } from 'react';
import { useParams } from 'react-router-dom';
import styled from 'styled-components';

import { ErrorBoundary, ErrorComponent, Input, Loading, SvgIcon } from '@/components/Common';
import { RecommendList, ProductSearchResultList, RecipeSearchResultList } from '@/components/Search';
import { SEARCH_PAGE_VARIANTS } from '@/constants';
import { useDebounce, useRoutePage } from '@/hooks/common';
import { useSearch } from '@/hooks/search';

const PRODUCT_PLACEHOLDER = '상품 이름을 검색해보세요.';
const RECIPE_PLACEHOLDER = '꿀조합에 포함된 상품을 입력해보세요.';

type SearchPageType = keyof typeof SEARCH_PAGE_VARIANTS;

const SearchPage = () => {
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
  const { routeBack } = useRoutePage();

  const theme = useTheme();

  const { searchVariant } = useParams();

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

  const isSearchVariant = (value: string): value is SearchPageType => {
    return value === 'products' || value === 'recipes';
  };

  if (!searchVariant || !isSearchVariant(searchVariant)) {
    return null;
  }

  const isProductSearchPage = searchVariant === 'products';
  const isRecipeSearchPage = searchVariant === 'recipes';
  const inputPlaceholder = isProductSearchPage ? PRODUCT_PLACEHOLDER : RECIPE_PLACEHOLDER;

  return (
    <>
      <SearchSection>
        <TitleWrapper>
          <Button type="button" variant="transparent" onClick={routeBack} aria-label="뒤로 가기">
            <SvgIcon variant="arrow" color={theme.colors.gray5} width={15} height={15} />
          </Button>
          <HeadingTitle>{SEARCH_PAGE_VARIANTS[searchVariant]} 검색</HeadingTitle>
        </TitleWrapper>
        <Spacing size={16} />
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
      <SearchResultSection>
        {isSubmitted && debouncedSearchQuery ? (
          <>
            <Heading as="h2" size="lg" weight="regular">
              <Mark>&apos;{searchQuery}&apos;</Mark>에 대한 검색결과입니다.
            </Heading>
            <ErrorBoundary fallback={ErrorComponent}>
              <Suspense fallback={<Loading />}>
                <Spacing size={20} />
                {isProductSearchPage && <ProductSearchResultList searchQuery={debouncedSearchQuery} />}
                {isRecipeSearchPage && <RecipeSearchResultList searchQuery={debouncedSearchQuery} />}
              </Suspense>
            </ErrorBoundary>
          </>
        ) : (
          <Text>{SEARCH_PAGE_VARIANTS[searchVariant]}을 검색해보세요.</Text>
        )}
      </SearchResultSection>
    </>
  );
};

export default SearchPage;

const SearchSection = styled.section`
  position: relative;
`;

const SearchResultSection = styled.section`
  margin-top: 30px;
`;

const TitleWrapper = styled.div`
  display: flex;
  gap: 12px;
  align-items: center;
`;

const HeadingTitle = styled(Heading)`
  font-size: 2.4rem;
`;

const Mark = styled.mark`
  font-weight: ${({ theme }) => theme.fontWeights.bold};
  background-color: ${({ theme }) => theme.backgroundColors.default};
`;
