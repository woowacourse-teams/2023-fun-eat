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
}

const RecommendList = ({ searchQuery, handleSearchClick }: RecommendListProps) => {
  const { data: searchResponse, fetchNextPage, hasNextPage } = useInfiniteProductSearchAutocompleteQuery(searchQuery);
  const scrollRef = useRef<HTMLDivElement>(null);
  useIntersectionObserver<HTMLDivElement>(fetchNextPage, scrollRef, hasNextPage);

  const products = searchResponse.pages.flatMap((page) => page.products);

  if (products.length === 0) {
    return <ErrorText>검색어가 포함된 상품을 찾지 못했어요</ErrorText>;
  }

  return (
    <>
      <RecommendListContainer>
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
      </RecommendListContainer>
      <div ref={scrollRef} aria-hidden />
    </>
  );
};

export default RecommendList;

const RecommendListContainer = styled.ul`
  height: 100%;

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
