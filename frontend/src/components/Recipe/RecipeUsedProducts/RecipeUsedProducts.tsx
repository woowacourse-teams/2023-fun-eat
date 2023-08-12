import { Badge, Button, Heading, Spacing, Text, useTheme } from '@fun-eat/design-system';
import styled from 'styled-components';

import { Input, SvgIcon } from '@/components/Common';
import type { RecipeUsedProduct } from '@/types/recipe';

interface RecipeUsedProductsProps {
  usedProducts: RecipeUsedProduct[];
  removeUsedProducts: (id: number) => void;
}

const RecipeUsedProducts = ({ usedProducts, removeUsedProducts }: RecipeUsedProductsProps) => {
  const theme = useTheme();

  return (
    <>
      <Heading as="h2" size="xl" tabIndex={0}>
        사용한 상품
        <RequiredMark aria-label="필수 작성">*</RequiredMark>
      </Heading>
      <Spacing size={12} />
      {/* TODO: 검색 컴포넌트로 교체하기 */}
      <Input />
      <Spacing size={12} />
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
        <ProductUploadLimitMessage color={theme.textColors.info}>
          사용한 상품은 6개까지 업로드 할 수 있어요 😉
        </ProductUploadLimitMessage>
      )}
    </>
  );
};

export default RecipeUsedProducts;

const RequiredMark = styled.sup`
  color: ${({ theme }) => theme.colors.error};
`;

const BadgeWrapper = styled.ul`
  display: flex;
  flex-wrap: wrap;
  column-gap: 8px;
  max-width: 300px;
  height: 56px;
`;

const ProductUploadLimitMessage = styled(Text)`
  height: 56px;
`;

const RemoveButton = styled(Button)`
  margin-left: 4px;
`;
