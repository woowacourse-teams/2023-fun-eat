import { Link, Text } from '@fun-eat/design-system';
import { useRef } from 'react';
import { Link as RouterLink } from 'react-router-dom';
import styled from 'styled-components';

import { ProductItem } from '@/components/Product';
import { PATH } from '@/constants/path';
import { useIntersectionObserver } from '@/hooks/common';
import { useInfiniteSearchedProductsQuery } from '@/hooks/queries/search';

interface SearchedListProps {
  searchQuery: string;
}

const SearchedList = ({ searchQuery }: SearchedListProps) => {
  const { data: searchResponse, fetchNextPage, hasNextPage } = useInfiniteSearchedProductsQuery(searchQuery);
  const scrollRef = useRef<HTMLDivElement>(null);
  useIntersectionObserver<HTMLDivElement>(fetchNextPage, scrollRef, hasNextPage);

  const products = searchResponse.pages.flatMap((page) => page.products);

  if (products.length === 0) {
    return <Text>검색한 상품을 찾을 수 없습니다.</Text>;
  }

  return (
    <>
      <SearchedListContainer>
        {products.map((product) => (
          <li key={product.id}>
            <Link as={RouterLink} to={`${PATH.PRODUCT_LIST}/food/${product.id}`}>
              <ProductItem product={product} />
            </Link>
          </li>
        ))}
      </SearchedListContainer>
      <div ref={scrollRef} aria-hidden />
    </>
  );
};

export default SearchedList;

const SearchedListContainer = styled.ul`
  display: flex;
  flex-direction: column;

  & > li {
    border-bottom: 1px solid ${({ theme }) => theme.borderColors.disabled};
  }
`;
