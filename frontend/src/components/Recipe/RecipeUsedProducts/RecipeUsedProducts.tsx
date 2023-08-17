import { Badge, Button, Heading, Text, useTheme } from '@fun-eat/design-system';
import { useState } from 'react';
import styled from 'styled-components';

import SearchedProductList from './SearchedProductList';

import { Input, SvgIcon } from '@/components/Common';
import { useDebounce } from '@/hooks/common';
import { useRecipeFormActionContext } from '@/hooks/context';
import { useSearch } from '@/hooks/search';
import type { RecipeProduct } from '@/types/recipe';

const MAX_USED_PRODUCTS_COUNT = 6;

const RecipeUsedProducts = () => {
  const theme = useTheme();

  const { searchQuery, handleSearchQuery } = useSearch();
  const [debouncedSearchQuery, setDebouncedSearchQuery] = useState(searchQuery || '');
  useDebounce(
    () => {
      setDebouncedSearchQuery(searchQuery);
    },
    200,
    [searchQuery]
  );

  const [usedProducts, setUsedProducts] = useState<RecipeProduct[]>([]);
  const { handleRecipeFormValue } = useRecipeFormActionContext();

  const removeUsedProducts = (id: number) => {
    setUsedProducts((prev) => prev.filter((usedProduct) => usedProduct.id !== id));
    handleRecipeFormValue({ target: 'productIds', value: id, action: 'remove' });
  };

  const addUsedProducts = (id: number, name: string) => {
    setUsedProducts((prev) => {
      if (prev.some((product) => product.id === id)) return prev;
      return [...prev, { id: id, name: name }];
    });
    handleRecipeFormValue({ target: 'productIds', value: id, action: 'add' });
  };

  return (
    <>
      <Heading as="h2" size="xl" tabIndex={0}>
        사용한 상품
        <RequiredMark aria-label="필수 작성">*</RequiredMark>
      </Heading>
      {usedProducts.length ? (
        <BadgeWrapper>
          {usedProducts.map(({ id, name }) => (
            <li key={id}>
              <Badge color={theme.colors.secondary} textColor={theme.textColors.default}>
                {name}
                <RemoveButton type="button" variant="transparent" onClick={() => removeUsedProducts(id)}>
                  <SvgIcon variant="close" width={8} height={8} />
                </RemoveButton>
              </Badge>
            </li>
          ))}
        </BadgeWrapper>
      ) : (
        <ProductUploadLimitMessage color={theme.textColors.disabled}>
          사용한 상품은 6개까지 업로드 할 수 있어요 😉
        </ProductUploadLimitMessage>
      )}
      <SearchInputWrapper>
        <Input
          placeholder="상품 이름을 검색해보세요."
          rightIcon={<SvgIcon variant="search" width={20} height={20} />}
          value={usedProducts.length === MAX_USED_PRODUCTS_COUNT ? '' : searchQuery}
          onChange={handleSearchQuery}
          disabled={usedProducts.length === MAX_USED_PRODUCTS_COUNT}
        />
        {usedProducts.length < MAX_USED_PRODUCTS_COUNT && debouncedSearchQuery && (
          <SearchedProductList searchQuery={debouncedSearchQuery} addUsedProducts={addUsedProducts} />
        )}
      </SearchInputWrapper>
    </>
  );
};

export default RecipeUsedProducts;

const RequiredMark = styled.sup`
  color: ${({ theme }) => theme.colors.error};
`;

const BadgeWrapper = styled.ul`
  display: flex;
  flex-direction: column;
  flex-wrap: wrap;
  align-content: flex-start;
  column-gap: 8px;
  height: 48px;
  overflow-x: auto;
`;

const ProductUploadLimitMessage = styled(Text)`
  display: flex;
  align-items: center;
  height: 48px;
`;

const SearchInputWrapper = styled.div`
  height: 100px;
`;

const RemoveButton = styled(Button)`
  margin-left: 4px;
`;
