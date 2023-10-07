import styled from 'styled-components';

import CategoryItem from '../CategoryItem/CategoryItem';

import { CATEGORY_TYPE } from '@/constants';
import { useGA } from '@/hooks/common';
import { useCategoryStoreQuery } from '@/hooks/queries/product';

const categoryType = CATEGORY_TYPE.STORE;

const CategoryStoreList = () => {
  const { data: categories } = useCategoryStoreQuery(categoryType);

  return (
    <CategoryStoreListWrapper>
      {categories.map(({ id, name, image }) => (
        <CategoryItem key={id} categoryId={id} name={name} image={image} categoryType={categoryType} />
      ))}
    </CategoryStoreListWrapper>
  );
};

export default CategoryStoreList;

const CategoryStoreListWrapper = styled.div`
  display: flex;
  gap: 16px;
`;
