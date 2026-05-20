package team.jeonghokim.daedongyeojido.domain.onepager.exception;

import team.jeonghokim.daedongyeojido.global.error.exception.DaedongException;
import team.jeonghokim.daedongyeojido.global.error.exception.ErrorCode;

public class OnePagerStateReasonInvalidException extends DaedongException {
  public static final DaedongException EXCEPTION = new OnePagerStateReasonInvalidException();

  private OnePagerStateReasonInvalidException() {
    super(ErrorCode.ONE_PAGER_STATE_REASON_INVALID);
  }
}
