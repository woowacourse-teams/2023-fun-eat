import { Button, Heading, Spacing } from '@fun-eat/design-system';
import type { ChangeEventHandler, FormEventHandler } from 'react';
import { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import styled from 'styled-components';

import { Input, SectionTitle, SvgIcon } from '@/components/Common';
import { IMAGE_SRC_PATH } from '@/constants/path';
import { useImageUploader } from '@/hooks/common';
import { useMemberModifyMutation, useMemberQuery } from '@/hooks/queries/members';
import { isChangedImage } from '@/utils/image';

const MemberModifyPage = () => {
  const { data: member } = useMemberQuery();

  const { previewImage, imageUrl, uploadImage } = useImageUploader();
  const [nickname, setNickname] = useState(member?.nickname ?? '');
  const { mutate } = useMemberModifyMutation();

  const navigate = useNavigate();

  if (!member) {
    return null;
  }

  const modifyNickname: ChangeEventHandler<HTMLInputElement> = (event) => {
    setNickname(event.target.value);
  };

  const handleSubmit: FormEventHandler<HTMLFormElement> = async (event) => {
    event.preventDefault();

    if (imageUrl === null) {
      return;
    }

    mutate(
      { nickname, image: imageUrl },
      {
        onSuccess: () => {
          navigate('/members');
        },
        onError: (error) => {
          if (error instanceof Error) {
            alert(error.message);
            return;
          }

          alert('회원정보 수정을 다시 시도해주세요.');
        },
      }
    );
  };

  return (
    <>
      <SectionTitle name="" />
      <MemberForm onSubmit={handleSubmit}>
        <div>
          <MemberImageUploaderContainer>
            <MemberImageUploaderWrapper>
              <UserProfileImageWrapper>
                <ProfileImage
                  src={
                    previewImage
                      ? previewImage
                      : isChangedImage(member.profileImage)
                      ? IMAGE_SRC_PATH + member.profileImage
                      : member.profileImage
                  }
                  alt="업로드한 사진"
                  width={80}
                />
              </UserProfileImageWrapper>
              <UserImageUploaderLabel>
                <input type="file" accept="image/*" onChange={uploadImage} />
                <SvgIcon variant="camera" width={20} height={20} />
              </UserImageUploaderLabel>
            </MemberImageUploaderWrapper>
          </MemberImageUploaderContainer>
          <Spacing size={44} />
          <Heading as="h2" size="xl" tabIndex={0}>
            닉네임
          </Heading>
          <Spacing size={12} />
          <Input value={nickname} customWidth="100%" onChange={modifyNickname} />
        </div>
        <FormButton customWidth="100%" customHeight="60px" size="xl" weight="bold">
          수정하기
        </FormButton>
      </MemberForm>
    </>
  );
};

export default MemberModifyPage;

const MemberImageUploaderContainer = styled.div`
  display: flex;
  justify-content: center;
`;

const MemberImageUploaderWrapper = styled.div`
  position: relative;
`;

const UserProfileImageWrapper = styled.div`
  width: 80px;
  height: 80px;
  background: ${({ theme }) => theme.backgroundColors.default};
  border: 1px solid ${({ theme }) => theme.borderColors.disabled};
  border-radius: 50%;
  overflow: hidden;
`;

const ProfileImage = styled.img`
  width: 100%;
  height: 100%;
  object-fit: cover;
`;

const UserImageUploaderLabel = styled.label`
  position: absolute;
  top: 54px;
  right: -5px;
  width: 30px;
  height: 30px;
  background: ${({ theme }) => theme.backgroundColors.default};
  border: 1px solid ${({ theme }) => theme.borderColors.disabled};
  border-radius: 50%;
  text-align: center;
  cursor: pointer;

  & > input {
    display: none;
  }

  & > svg {
    transform: translateX(-0.5px) translateY(3px);
  }
`;

const MemberForm = styled.form`
  height: 92%;
  display: flex;
  flex-direction: column;
  justify-content: space-between;
`;

const FormButton = styled(Button)`
  background: ${({ theme }) => theme.colors.primary};
  color: ${({ theme }) => theme.colors.black};
`;
