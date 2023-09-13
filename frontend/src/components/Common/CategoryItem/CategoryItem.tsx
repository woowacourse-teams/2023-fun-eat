import styled from 'styled-components';

import type { Category } from '@/types/common';

interface CategoryItemProps {
  category: Category;
}

const CategoryItem = ({ category }: CategoryItemProps) => {
  const { name, image } = category;

  return (
    <CategoryItemContainer>
      <ImageWrapper>
        <img src={image} width={60} height={60} alt="카테고리" />
      </ImageWrapper>
      <CategoryName>{name}</CategoryName>
    </CategoryItemContainer>
  );
};

export default CategoryItem;

const CategoryItemContainer = styled.div`
  width: 70px;
  height: 100px;
  text-align: center;
`;

const ImageWrapper = styled.div`
  display: flex;
  justify-content: center;
  align-items: center;
  width: 70px;
  height: 70px;
  border-radius: 10px;
  background: ${({ theme }) => theme.colors.white};
`;

const CategoryName = styled.p`
  margin-top: 10px;
  font-weight: 600;
`;
