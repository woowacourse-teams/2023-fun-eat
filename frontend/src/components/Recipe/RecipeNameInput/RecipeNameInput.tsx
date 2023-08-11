import { Heading, Spacing } from '@fun-eat/design-system';
import type { ChangeEventHandler } from 'react';
import styled from 'styled-components';

import { Input } from '@/components/Common';

interface RecipeNameInputProps {
  recipeName: string;
  handleRecipeName: ChangeEventHandler<HTMLInputElement>;
}

const RecipeNameInput = ({ handleRecipeName, recipeName }: RecipeNameInputProps) => {
  return (
    <>
      <Heading as="h2" size="xl" tabIndex={0}>
        꿀조합 이름
        <RequiredMark aria-label="필수 작성">*</RequiredMark>
      </Heading>
      <Spacing size={12} />
      <Input placeholder="재치있는 이름을 지어주세요!" value={recipeName} onChange={handleRecipeName} />
    </>
  );
};

export default RecipeNameInput;

const RequiredMark = styled.sup`
  color: ${({ theme }) => theme.colors.error};
`;
