import { Text } from '@fun-eat/design-system';
import { useRef } from 'react';
import styled from 'styled-components';

import { MarkedText } from '@/components/Common';
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

  const products = searchResponse.pages.flatMap((page) => page.products);

  if (products.length === 0) {
    return <ErrorText>검색어에 해당 하는 상품이 없습니다.</ErrorText>;
  }

  const productNames = products.map((product) => product.name);

  return (
    <RecommendListContainer>
      {productNames.map((recommend) => (
        <li key={recommend}>
          <RecommendText>
            <MarkedText text={recommend} mark={searchQuery} />
          </RecommendText>
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

const RecommendText = styled(Text)`
  line-height: 36px;
`;

const ErrorText = styled(Text)`
  height: 36px;
  padding: 0 10px;
  line-height: 36px;
`;
