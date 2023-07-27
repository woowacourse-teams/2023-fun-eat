import { Button, Checkbox, Divider, Heading, Spacing, Text, theme, useBottomSheet } from '@fun-eat/design-system';
import styled from 'styled-components';

import ReviewImageUploader from '../ReviewImageUploader/ReviewImageUploader';
import ReviewTagList from '../ReviewTagList/ReviewTagList';
import ReviewTextarea from '../ReviewTextarea/ReviewTextarea';
import StarRate from '../StarRate/StarRate';

import { SvgIcon } from '@/components/Common';
import { ProductOverviewItem } from '@/components/Product';
import productDetail from '@/mocks/data/productDetail.json';

interface ReviewRegisterFormProps {
  close: () => void;
}

const ReviewRegisterForm = ({ close }: ReviewRegisterFormProps) => {
  return (
    <ReviewRegisterFormContainer>
      <RegisterFormHeader>
        <Heading css="font-size:2.4rem">리뷰 작성</Heading>
        <CloseButton variant="transparent" onClick={close}>
          <SvgIcon variant="close" color={theme.colors.black} width={20} height={20} />
        </CloseButton>
      </RegisterFormHeader>
      <Divider />
      <ProductOverviewItemWrapper>
        <ProductOverviewItem name={productDetail.name} image={productDetail.image} />
      </ProductOverviewItemWrapper>
      <Divider variant="disabled" css="height:4px;" />
      <RegisterFormWrapper>
        <form>
          <ReviewImageUploader />
          <Spacing size={60} />
          <StarRate />
          <Spacing size={60} />
          <ReviewTagList />
          <Spacing size={60} />
          <ReviewTextarea />
          <Spacing size={80} />
          <ReBuyCheckWrapper>
            <Checkbox />
            <Text weight="bold"> 재구매할 생각이 있으신가요?</Text>
          </ReBuyCheckWrapper>
          <Spacing size={16} />
          <Button width="100%" height="60px" size="xl">
            등록하기
          </Button>
        </form>
      </RegisterFormWrapper>
    </ReviewRegisterFormContainer>
  );
};

export default ReviewRegisterForm;

const ReviewRegisterFormContainer = styled.div`
  height: 100%;
  padding: 30px;
`;

const RegisterFormHeader = styled.header`
  display: flex;
  align-items: center;
  justify-content: center;
  flex-direction: row;
  position: relative;
  height: 80px;
`;

const CloseButton = styled(Button)`
  position: absolute;
  right: 30px;
`;

const ProductOverviewItemWrapper = styled.div`
  margin: 15px 0;
`;

const RegisterFormWrapper = styled.div`
  padding: 50px 0;
`;

const ReBuyCheckWrapper = styled.div`
  display: flex;
  align-items: center;
`;
