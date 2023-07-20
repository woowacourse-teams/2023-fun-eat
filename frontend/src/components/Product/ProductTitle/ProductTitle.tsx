import { Button, Heading, theme } from '@fun-eat/design-system';
import { useState } from 'react';
import styled from 'styled-components';

import { SvgIcon } from '@/components/Common';
import productDetail from '@/mocks/data/productDetail.json';

const ProductTitle = () => {
  const { name, bookmark } = productDetail;
  const [isBookmarked, setIsBookmarked] = useState(bookmark);

  const toggleBookmark = () => {
    setIsBookmarked(!isBookmarked);
  };

  return (
    <ProductTitleContainer>
      <ProductTitleWrapper>
        <Button color="white" variant="filled">
          <SvgIcon variant="arrow" color={theme.colors.gray5} width={15} height={15} />
        </Button>
        <Heading size="xl" css="margin-left: 10px">
          {name}
        </Heading>
      </ProductTitleWrapper>
      <Button color="white" variant="filled" css="width: 2rem" onClick={toggleBookmark}>
        <SvgIcon
          variant={isBookmarked ? 'bookmarkFilled' : 'bookmark'}
          color={isBookmarked ? theme.colors.primary : theme.colors.gray5}
        />
      </Button>
    </ProductTitleContainer>
  );
};

export default ProductTitle;

const ProductTitleContainer = styled.div`
  display: flex;
  align-items: center;
  justify-content: space-between;
`;

const ProductTitleWrapper = styled.div`
  display: flex;
  align-items: center;
`;
