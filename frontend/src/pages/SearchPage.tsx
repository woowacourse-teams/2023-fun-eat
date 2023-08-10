import { Heading, Spacing } from '@fun-eat/design-system';
import type { ChangeEventHandler } from 'react';
import { useState, useTransition } from 'react';
import styled from 'styled-components';

import { Input, SvgIcon, TabMenu } from '@/components/Common';
import { RecommendList, SearchedList } from '@/components/Search';
import useDebounce from '@/hooks/useDebounce';

const SearchPage = () => {
  const [, startTransition] = useTransition();
  const [searchQuery, setSearchQuery] = useState('');
  const [debouncedSearchQuery, setDebouncedSearchQuery] = useState('');

  const handleSearchQuery: ChangeEventHandler<HTMLInputElement> = (event) => {
    startTransition(() => {
      setSearchQuery(event.currentTarget.value);
    });
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
        <Input
          customWidth="100%"
          placeholder="상품 이름을 검색해보세요."
          rightIcon={<SvgIcon variant="search" />}
          value={searchQuery}
          onChange={handleSearchQuery}
        />
        {debouncedSearchQuery && (
          <RecommendWrapper>
            <RecommendList searchQuery={debouncedSearchQuery} />
          </RecommendWrapper>
        )}
      </SearchSection>
      <Spacing size={20} />
      <TabMenu tabMenus={['상품', '꿀조합']} />
      <SearchResultSection>
        {debouncedSearchQuery ? (
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
