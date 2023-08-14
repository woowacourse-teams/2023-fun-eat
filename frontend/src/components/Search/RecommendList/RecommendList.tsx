import { Link, Text } from '@fun-eat/design-system';
import { useRef } from 'react';
import { Link as RouterLink } from 'react-router-dom';
import styled from 'styled-components';

import { MarkedText } from '@/components/Common';
import { PATH } from '@/constants/path';
import { useIntersectionObserver } from '@/hooks/common';
import { useInfiniteSearchedProductsQuery } from '@/hooks/queries/search';

interface RecommendListProps {
  searchQuery: string;
}

const RecommendList = ({ searchQuery }: RecommendListProps) => {
  const { data: searchResponse, fetchNextPage, hasNextPage } = useInfiniteSearchedProductsQuery(searchQuery);
  const scrollRef = useRef<HTMLDivElement>(null);
  useIntersectionObserver<HTMLDivElement>(fetchNextPage, scrollRef, hasNextPage);

  const products = searchResponse.pages
    .flatMap((page) => page.products)
    .map((product) => ({
      id: product.id,
      name: product.name,
      category: product.category,
    }));

  if (products.length === 0) {
    return <ErrorText>검색어에 해당 하는 상품이 없습니다.</ErrorText>;
  }

  return (
    <RecommendListContainer>
      {products.map(({ id, name }) => (
        <li key={id}>
          <Link as={RouterLink} to={`${PATH.PRODUCT_LIST}/food/${id}`} block>
            <MarkedText text={name} mark={searchQuery} />
          </Link>
        </li>
      ))}
    </RecommendListContainer>
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

const ErrorText = styled(Text)`
  height: 36px;
  padding: 0 10px;
  line-height: 36px;
`;
