import { Button, Text } from '@fun-eat/design-system';
import type { MouseEventHandler } from 'react';
import { useRef } from 'react';
import styled from 'styled-components';

import { MarkedText } from '@/components/Common';
import { useIntersectionObserver } from '@/hooks/common';
import { useInfiniteProductSearchAutocompleteQuery } from '@/hooks/queries/search';

interface RecommendListProps {
  searchQuery: string;
  handleSearchClick: MouseEventHandler<HTMLButtonElement>;
  isAutocompleteOpen: boolean;
  handleAutocompleteClose: MouseEventHandler<HTMLDivElement>;
}

const RecommendList = ({
  searchQuery,
  handleSearchClick,
  isAutocompleteOpen,
  handleAutocompleteClose,
}: RecommendListProps) => {
  const { data: searchResponse, fetchNextPage, hasNextPage } = useInfiniteProductSearchAutocompleteQuery(searchQuery);
  const scrollRef = useRef<HTMLDivElement>(null);
  useIntersectionObserver<HTMLDivElement>(fetchNextPage, scrollRef, hasNextPage);

  const products = searchResponse.pages.flatMap((page) => page.products);

  if (products.length === 0) {
    return <ErrorText>검색어가 포함된 상품을 찾지 못했어요</ErrorText>;
  }

  return (
    <RecommendListContainer isAutocompleteOpen={isAutocompleteOpen}>
      <Backdrop onClick={handleAutocompleteClose} />
      <RecommendListWrapper>
        {products.map(({ id, name }) => (
          <li key={id}>
            <ProductButton
              type="button"
              customWidth="100%"
              customHeight="100%"
              color="white"
              value={name}
              onClick={handleSearchClick}
            >
              <MarkedText text={name} mark={searchQuery} />
            </ProductButton>
          </li>
        ))}
      </RecommendListWrapper>
      <div ref={scrollRef} aria-hidden />
    </RecommendListContainer>
  );
};

export default RecommendList;

const Backdrop = styled.div`
  position: fixed;
  top: 0;
  right: 0;
  bottom: 0;
  left: 0;
`;

const RecommendListContainer = styled.div<{ isAutocompleteOpen: boolean }>`
  display: ${({ isAutocompleteOpen }) => (isAutocompleteOpen ? 'block' : 'none')};
  position: absolute;
  top: 100%;
  left: 0;
  right: 0;
  max-height: 150px;
  padding: 10px 0;
  background-color: #ffffff;
  border: 1px solid #a0a0a0;
  overflow-y: auto;
`;

const RecommendListWrapper = styled.ul`
  position: relative;
  width: 100%;

  & > li {
    height: 36px;
    line-height: 36px;
    padding: 0 10px;
  }
`;

const ProductButton = styled(Button)`
  text-align: left;
`;

const ErrorText = styled(Text)`
  height: 36px;
  padding: 0 10px;
  line-height: 36px;
`;
