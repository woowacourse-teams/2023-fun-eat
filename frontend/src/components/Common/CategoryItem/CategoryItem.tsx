import { Button } from '@fun-eat/design-system';
import { useNavigate } from 'react-router-dom';
import styled from 'styled-components';

import { PATH } from '@/constants/path';
import { useCategoryActionContext } from '@/hooks/context';

interface CategoryItemProps {
  categoryId: number;
  name: string;
  image: string;
  categoryType: 'food' | 'store';
}

const CategoryItem = ({ categoryId, name, image, categoryType }: CategoryItemProps) => {
  const navigate = useNavigate();
  const { selectCategory } = useCategoryActionContext();

  const handleCategoryItemClick = (categoryId: number) => {
    selectCategory(categoryType, categoryId);
    navigate(PATH.PRODUCT_LIST + '/' + categoryType);
  };

  return (
    <Button type="button" variant="transparent" customHeight="auto" onClick={() => handleCategoryItemClick(categoryId)}>
      <ImageWrapper>
        <img src={image} width={60} height={60} alt={name} />
      </ImageWrapper>
      <CategoryName>{name}</CategoryName>
    </Button>
  );
};

export default CategoryItem;

const ImageWrapper = styled.div`
  display: flex;
  justify-content: center;
  align-items: center;
  width: 60px;
  height: 60px;
  border-radius: 10px;
  background: ${({ theme }) => theme.colors.white};

  & > img {
    width: 100%;
    height: auto;
    object-fit: cover;
  }
`;

const CategoryName = styled.p`
  margin-top: 10px;
  font-weight: 600;
  font-size: ${({ theme }) => theme.fontSizes.xs};
`;
