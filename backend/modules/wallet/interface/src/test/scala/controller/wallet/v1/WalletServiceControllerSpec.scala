package controller.wallet.v1

// import api.osaifu.wallet.v1._

// import memory.WalletMemoryRepository
// import repository.WalletRepository
// import interactor.CreateWalletInteractor
// import akka.actor.testkit.typed.scaladsl.ActorTestKit
// import akka.actor.typed.ActorSystem

// import org.scalatest.BeforeAndAfterAll
// import org.scalatest.concurrent.ScalaFutures
// import org.scalatest.matchers.should.Matchers
// import org.scalatest.wordspec.AnyWordSpec

// import scala.concurrent.duration._

// class WalletServiceControllerSpec
//     extends AnyWordSpec
//     with BeforeAndAfterAll
//     with Matchers
//     with ScalaFutures {

//   val testKit: ActorTestKit = ActorTestKit()

//   implicit val patience: PatienceConfig = PatienceConfig(scaled(5.seconds), scaled(100.millis))

//   implicit val sys: ActorSystem[_] = testKit.system

//   val walletRepository: WalletRepository = new WalletMemoryRepository()
//   val create: CreateWalletInteractor     = new CreateWalletInteractor(walletRepository)

//   val service = new WalletServiceController(create)

//   override def afterAll(): Unit = {
//     testKit.shutdownTestKit()
//   }

//   "WalletServiceImpl" should {
//     "OKなレスポンスを返す" in {
//       val res = service.create(CreateRequest(owner = "alice"))
//       res.futureValue should ===(
//         CreateResponse(Some(Wallet(id = "dummyid", owner = "alice", balance = "2000")))
//       )
//     }
//   }
// }
