import { Heading, Spacing } from '@fun-eat/design-system';
import styled from 'styled-components';

import { Input, SvgIcon, TabMenu } from '@/components/Common';
import { ProductItem } from '@/components/Product';
import mockProducts from '@/mocks/data/products.json';

const SearchPage = () => {
  const { products } = mockProducts;

  return (
    <>
      <Input customWidth="100%" placeholder="상품 이름을 검색해보세요." rightIcon={<SvgIcon variant="search" />} />
      <Spacing size={20} />
      <TabMenu tabMenus={['상품', '꿀조합']} />
      <SearchResultSection>
        <Heading as="h2" size="lg" weight="regular">
          <MarkedText>&apos;담곰이&apos;</MarkedText>에 대한 검색결과입니다.
        </Heading>
        <Spacing size={20} />
        <ProductListWrapper>
          {products.map((product) => (
            <li key={product.id}>
              <ProductItem product={product} />
            </li>
          ))}
        </ProductListWrapper>
      </SearchResultSection>
    </>
  );
};

export default SearchPage;

const SearchResultSection = styled.section`
  margin-top: 30px;
`;

const MarkedText = styled.mark`
  background-color: ${({ theme }) => theme.backgroundColors.default};
  font-weight: ${({ theme }) => theme.fontWeights.bold};
`;

const ProductListWrapper = styled.ul`
  display: flex;
  flex-direction: column;

  & > li {
    border-bottom: 1px solid ${({ theme }) => theme.borderColors.disabled};
  }
`;
