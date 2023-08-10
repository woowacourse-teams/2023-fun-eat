import { Text } from '@fun-eat/design-system';
import styled from 'styled-components';

import { useSearchedProductsQuery } from '@/hooks/queries/search';

interface RecommendListProps {
  searchQuery: string;
}

const RecommendList = ({ searchQuery }: RecommendListProps) => {
  const { data: searchResponse } = useSearchedProductsQuery(searchQuery);

  if (!searchResponse) {
    return null;
  }

  const { products } = searchResponse;

  if (products.length === 0) {
    return <p>검색어에 해당 하는 상품이 없습니다.</p>;
  }

  const productNames = products.map((product) => product.name);

  return (
    <RecommendListContainer>
      {productNames.map((recommend) => (
        <li key={recommend}>
          <RecommendText>{recommend}</RecommendText>
        </li>
      ))}
    </RecommendListContainer>
  );
};

export default RecommendList;

const RecommendListContainer = styled.ul`
  height: & > li {
    height: 36px;
    line-height: 36px;
    padding: 0 10px;
  }
`;

const RecommendText = styled(Text)`
  line-height: 36px;
`;
