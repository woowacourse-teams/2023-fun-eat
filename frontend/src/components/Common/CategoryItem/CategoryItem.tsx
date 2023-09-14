import { Button } from '@fun-eat/design-system';
import styled from 'styled-components';

interface CategoryItemProps {
  name: string;
  image: string;
}

const CategoryItem = ({ name, image }: CategoryItemProps) => {
  return (
    <CategoryItemContainer variant="transparent">
      <ImageWrapper>
        <img src={image} width={60} height={60} alt="카테고리" />
      </ImageWrapper>
      <CategoryName>{name}</CategoryName>
    </CategoryItemContainer>
  );
};

export default CategoryItem;

const CategoryItemContainer = styled(Button)`
  width: 60px;
  height: 100px;
  text-align: center;
`;

const ImageWrapper = styled.div`
  display: flex;
  justify-content: center;
  align-items: center;
  width: 60px;
  height: 60px;
  border-radius: 10px;
  background: ${({ theme }) => theme.colors.white};

  img {
    width: 100%;
    height: auto;
    object-fit: cover;
  }
`;

const CategoryName = styled.p`
  margin-top: 10px;
  font-weight: 600;
  font-size: 13px;
`;
