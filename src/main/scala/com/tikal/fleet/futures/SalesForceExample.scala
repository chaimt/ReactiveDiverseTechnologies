package com.tikal.fleet.futures

import com.tikal.fleet.sf.{PurchaseAudit, SFData, SFResult}

import scala.concurrent.Future
import scala.util.{Failure, Success}
import scala.concurrent.ExecutionContext.Implicits.global

/**
  * Created by chaimturkel on 7/20/16.
  */
class SalesForceExample {

  case class RequestWrapper(audit: PurchaseAudit, sfData: SFData)
  case class ResultWrapper(audit: PurchaseAudit, sfResult: SFResult)

  def getAuditDataFromSF(audit: PurchaseAudit): SFData = new SFData
  def sendUpdateToSF(audit: PurchaseAudit, sfData: SFData): SFResult = new SFResult
  def saveToDB(audit: PurchaseAudit, sfResult: SFResult): Unit ={}

  def sendToSF(purchaseAudit: List[PurchaseAudit]): Unit = {
    val sfUpdateList: List[Future[ResultWrapper]] =
      for (audit: PurchaseAudit <- purchaseAudit)
        yield {
          val map: Future[ResultWrapper] = Future {
            RequestWrapper(audit, getAuditDataFromSF(audit))
          }
            .map(wrapper => {
              ResultWrapper(wrapper.audit, sendUpdateToSF(wrapper.audit, wrapper.sfData))
            })
          map
        }

    val futureList: Future[List[ResultWrapper]] = Future.sequence(sfUpdateList)
    futureList onComplete {
      case Success(wrapperList) => {
        for (wrapper <- wrapperList) {
          saveToDB(wrapper.audit, wrapper.sfResult)
        }
      }
      case Failure(error) => {
//        log.error(error.getMessageƒ)
      }
    }
  }


}
