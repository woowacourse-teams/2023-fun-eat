import { Button, Text, theme } from '@fun-eat/design-system';
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
      <div>
        <Button color="white" variant="filled">
          <SvgIcon variant="arrow" color={theme.colors.gray5} width={15} height={15} />
        </Button>
        <Text as="span" css="margin-left: 10px">
          {name}
        </Text>
      </div>
      <Button
        color="white"
        variant="filled"
        css={`
          width: 2rem;
        `}
        onClick={toggleBookmark}
      >
        {isBookmarked ? (
          <SvgIcon variant="bookmarkFilled" color={theme.colors.primary} />
        ) : (
          <SvgIcon variant="bookmark" color={theme.colors.gray5} />
        )}
      </Button>
    </ProductTitleContainer>
  );
};

export default ProductTitle;

const ProductTitleContainer = styled.h3`
  display: flex;
  align-items: center;
  justify-content: space-between;
`;
