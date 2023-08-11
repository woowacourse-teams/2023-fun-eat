import { Heading, Spacing } from '@fun-eat/design-system';
import type { ChangeEventHandler } from 'react';

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
      </Heading>
      <Spacing size={12} />
      <Input onChange={handleRecipeName} value={recipeName} />
    </>
  );
};

export default RecipeNameInput;
