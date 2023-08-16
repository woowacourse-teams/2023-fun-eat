import { Button } from '@fun-eat/design-system';
import type { ComponentType, PropsWithChildren } from 'react';
import { Component } from 'react';

export interface FallbackProps {
  message: string;
}

interface ErrorBoundaryProps {
  handleReset?: () => void;
  fallback: ComponentType<FallbackProps>;
}

interface ErrorBoundaryState {
  error: Error | null;
}

class ErrorBoundary extends Component<PropsWithChildren<ErrorBoundaryProps>, ErrorBoundaryState> {
  state: ErrorBoundaryState = {
    error: null,
  };

  static getDerivedStateFromError(error: Error): ErrorBoundaryState {
    return { error };
  }

  resetError = () => {
    if (this.props.handleReset) {
      this.props.handleReset();
    }

    this.setState({ error: null });
  };

  render() {
    const { fallback: FallbackComponent } = this.props;

    if (this.state.error) {
      return (
        <>
          <FallbackComponent message={this.state.error.message} />
          <Button type="button" onClick={this.resetError}>
            다시 시도하기
          </Button>
        </>
      );
    }

    return this.props.children;
  }
}

export default ErrorBoundary;
