import { Button, Heading, Spacing } from '@fun-eat/design-system';
import type { ChangeEventHandler, FormEventHandler } from 'react';
import { useState } from 'react';
import { useSearchParams } from 'react-router-dom';
import styled from 'styled-components';

import { Input, SvgIcon, TabMenu } from '@/components/Common';
import { RecommendList, SearchedList } from '@/components/Search';
import { useSearch } from '@/hooks/queries/search';
import useDebounce from '@/hooks/useDebounce';

const SearchPage = () => {
  const { searchQuery, isSubmitted, handleSearchQuery, handleSubmit } = useSearch();
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
        <form onSubmit={handleSubmit}>
          <Input
            customWidth="100%"
            placeholder="상품 이름을 검색해보세요."
            rightIcon={
              <Button customHeight="38px" color="white">
                <SvgIcon variant="search" />
              </Button>
            }
            value={searchQuery}
            onChange={handleSearchQuery}
          />
        </form>
        {!isSubmitted && debouncedSearchQuery && (
          <RecommendWrapper>
            <RecommendList searchQuery={debouncedSearchQuery} />
          </RecommendWrapper>
        )}
      </SearchSection>
      <Spacing size={20} />
      <TabMenu tabMenus={['상품', '꿀조합']} />
      <SearchResultSection>
        {isSubmitted && debouncedSearchQuery ? (
          <>
            <Heading as="h2" size="lg" weight="regular">
              <MarkedText>&apos;{searchQuery}&apos;</MarkedText>에 대한 검색결과입니다.
            </Heading>
            <Spacing size={20} />
            <SearchedList searchQuery={debouncedSearchQuery} />
          </>
        ) : (
          <p>상품을 검색해보세요.</p>
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

const MarkedText = styled.mark`
  background-color: ${({ theme }) => theme.backgroundColors.default};
  font-weight: ${({ theme }) => theme.fontWeights.bold};
`;
