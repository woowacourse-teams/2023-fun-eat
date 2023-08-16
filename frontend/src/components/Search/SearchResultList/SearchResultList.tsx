import { Link, Text } from '@fun-eat/design-system';
import { useRef } from 'react';
import { Link as RouterLink } from 'react-router-dom';
import styled from 'styled-components';

import { ProductItem } from '@/components/Product';
import { PATH } from '@/constants/path';
import { useIntersectionObserver } from '@/hooks/common';
import { useInfiniteProductSearchResultsQuery } from '@/hooks/queries/search';

interface SearchResultListProps {
  searchQuery: string;
}

const SearchResultList = ({ searchQuery }: SearchResultListProps) => {
  const { data: searchResponse, fetchNextPage, hasNextPage } = useInfiniteProductSearchResultsQuery(searchQuery);
  const scrollRef = useRef<HTMLDivElement>(null);
  useIntersectionObserver<HTMLDivElement>(fetchNextPage, scrollRef, hasNextPage);

  const products = searchResponse.pages.flatMap((page) => page.products);

  if (products.length === 0) {
    return <Text>검색한 상품을 찾을 수 없습니다.</Text>;
  }

  return (
    <>
      <SearchResultListContainer>
        {products.map((product) => (
          <li key={product.id}>
            <Link as={RouterLink} to={`${PATH.PRODUCT_LIST}/${product.categoryType}/${product.id}`}>
              <ProductItem product={product} />
            </Link>
          </li>
        ))}
      </SearchResultListContainer>
      <div ref={scrollRef} aria-hidden />
    </>
  );
};

export default SearchResultList;

const SearchResultListContainer = styled.ul`
  display: flex;
  flex-direction: column;

  & > li {
    border-bottom: 1px solid ${({ theme }) => theme.borderColors.disabled};
  }
`;
