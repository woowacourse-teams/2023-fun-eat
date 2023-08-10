import { Link, Text } from '@fun-eat/design-system';
import { useRef } from 'react';
import { Link as RouterLink } from 'react-router-dom';
import styled from 'styled-components';

import { MarkedText } from '@/components/Common';
import { PATH } from '@/constants/path';
import { useSearchedProductsQuery } from '@/hooks/queries/search';
import useIntersectionObserver from '@/hooks/useIntersectionObserver';

interface RecommendListProps {
  searchQuery: string;
}

const RecommendList = ({ searchQuery }: RecommendListProps) => {
  const { data: searchResponse, fetchNextPage, hasNextPage } = useSearchedProductsQuery(searchQuery);
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
      category: product.category,
    }));

  if (products.length === 0) {
    return <ErrorText>검색어에 해당 하는 상품이 없습니다.</ErrorText>;
  }

  return (
    <RecommendListContainer>
      {products.map((product) => (
        <li key={product.id}>
          <Link as={RouterLink} to={`${PATH.PRODUCT_LIST}/food/${product.id}`} block>
            <MarkedText text={product.name} mark={searchQuery} />
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
