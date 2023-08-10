import { Button, Text, theme } from '@fun-eat/design-system';
import type { ComponentPropsWithRef, ForwardedRef, ReactNode } from 'react';
import { forwardRef } from 'react';
import styled from 'styled-components';

interface InputProps extends ComponentPropsWithRef<'input'> {
  /**
   *  Input 컴포넌트의 너비값입니다.
   */
  customWidth?: string;
  /**
   * Input value에 에러가 있는지 여부입니다.
   */
  isError?: boolean;
  /**
   * Input 컴포넌트 오른쪽에 위치할 아이콘입니다.
   */
  rightIcon?: ReactNode;
  /**
   * isError가 true일 때 보여줄 에러 메시지입니다.
   */
  errorMessage?: string;
}

const Input = forwardRef(
  (
    { customWidth = '300px', isError = false, rightIcon, errorMessage, ...props }: InputProps,
    ref: ForwardedRef<HTMLInputElement>
  ) => {
    return (
      <>
        <InputContainer customWidth={customWidth}>
          <CustomInput ref={ref} isError={isError} {...props} />
          {rightIcon && <IconWrapper variant="transparent">{rightIcon}</IconWrapper>}
        </InputContainer>
        {isError && <ErrorMessage>{errorMessage}</ErrorMessage>}
      </>
    );
  }
);

Input.displayName = 'Input';

export default Input;

type InputContainerStyleProps = Pick<InputProps, 'customWidth'>;
type CustomInputStyleProps = Pick<InputProps, 'isError'>;

const InputContainer = styled.div<InputContainerStyleProps>`
  position: relative;
  max-width: ${({ customWidth }) => customWidth};
  text-align: center;
`;

const CustomInput = styled.input<CustomInputStyleProps>`
  width: 100%;
  height: 40px;
  padding: 10px 0 10px 12px;
  color: ${({ isError }) => (isError ? theme.colors.error : theme.textColors.default)};
  border: 1px solid ${({ isError }) => (isError ? theme.colors.error : theme.borderColors.default)};
  border-radius: 5px;

  &:focus {
    outline: none;
    border: 2px solid ${({ isError }) => (isError ? theme.colors.error : theme.borderColors.strong)};
  }

  &::placeholder {
    font-size: ${theme.fontSizes.xs};
    color: ${theme.textColors.disabled};
  }
`;

const IconWrapper = styled(Button)`
  position: absolute;
  top: 0;
  right: 0;
  height: 100%;
  margin-right: 8px;
`;

const ErrorMessage = styled(Text)`
  font-size: ${theme.fontSizes.xs};
  color: ${theme.colors.error};
`;
