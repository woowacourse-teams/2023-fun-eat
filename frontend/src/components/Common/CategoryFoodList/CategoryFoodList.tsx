import styled from 'styled-components';

import CategoryItem from '../CategoryItem/CategoryItem';

import { CATEGORY_TYPE } from '@/constants';
import { useCategoryFoodQuery } from '@/hooks/queries/product';

const categoryType = CATEGORY_TYPE.FOOD;

const CategoryFoodList = () => {
  const { data: categories } = useCategoryFoodQuery(categoryType);

  return (
    <CategoryFoodListWrapper>
      {categories.map(({ id, name, image }) => (
        <CategoryItem key={id} categoryId={id} name={name} image={image} categoryType={categoryType} />
      ))}
    </CategoryFoodListWrapper>
  );
};

export default CategoryFoodList;

const CategoryFoodListWrapper = styled.div`
  display: flex;
  gap: 16px;
`;
