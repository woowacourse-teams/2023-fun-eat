import { Button, Text } from '@fun-eat/design-system';
import { useRef } from 'react';
import styled from 'styled-components';

import { MarkedText } from '@/components/Common';
import { useIntersectionObserver } from '@/hooks/common';
import { useInfiniteProductSearchAutocompleteQuery } from '@/hooks/queries/search';

interface SearchedProductListProps {
  searchQuery: string;
  addUsedProducts: (id: number, name: string) => void;
}

const SearchedProductList = ({ searchQuery, addUsedProducts }: SearchedProductListProps) => {
  const { data: searchResponse, fetchNextPage, hasNextPage } = useInfiniteProductSearchAutocompleteQuery(searchQuery);
  const scrollRef = useRef<HTMLDivElement>(null);
  useIntersectionObserver<HTMLDivElement>(fetchNextPage, scrollRef, hasNextPage);

  if (!searchResponse) {
    return null;
  }

  const products = searchResponse.pages
    .flatMap((page) => page.products)
    .map((product) => ({
      id: product.id,
      name: product.name,
    }));

  if (products.length === 0) {
    return <ErrorText>검색어에 해당 하는 상품이 없습니다.</ErrorText>;
  }

  return (
    <>
      <SearchedProductListContainer>
        {products.map(({ id, name }) => (
          <li key={id}>
            <Button type="button" variant="transparent" onClick={() => addUsedProducts(id, name)}>
              <MarkedText text={name} mark={searchQuery} />
            </Button>
          </li>
        ))}
      </SearchedProductListContainer>
      <div ref={scrollRef} aria-hidden />
    </>
  );
};

export default SearchedProductList;

const SearchedProductListContainer = styled.ul`
  width: 300px;
  height: 80%;
  border: 1px solid ${({ theme }) => theme.borderColors.default};
  border-top: none;
  border-radius: 0 0 5px 5px;
  overflow: auto;

  &::-webkit-scrollbar: horizontal {
    display: none;
  }

  & > li {
    height: 36px;
    line-height: 36px;
    padding: 0 10px;
  }
`;

const ErrorText = styled(Text)`
  height: 36px;
  padding: 0 10px;
  line-height: 36px;
`;
