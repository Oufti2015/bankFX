package sst.bank.controllers;

import javafx.fxml.FXML;
import javafx.scene.layout.GridPane;
import jfxtras.scene.control.gauge.linear.BasicRoundDailGauge;
import jfxtras.scene.control.gauge.linear.elements.PercentSegment;
import jfxtras.scene.control.gauge.linear.elements.Segment;
import lombok.extern.log4j.Log4j;
import sst.bank.OuftiBankFX;
import sst.bank.model.BankSummary;
import sst.bank.model.container.BankContainer;

@Log4j
public class SummaryPaneController {
    @FXML
    private SummaryListController summaryListController;
    @FXML
    private GridPane tachiAnchorPane;

    private BasicRoundDailGauge roundDailGauge = new BasicRoundDailGauge();

    @FXML
    private void initialize() {
	log.debug("initialize...");

	log.debug("aController " + summaryListController);

	if (summaryListController == null) {
	    log.fatal("aController is not initialised...");
	    OuftiBankFX.eventBus.post(new Exception("Controller not injected..."));
	}

	roundDailGauge.getStyleClass().add("colorscheme-red-to-blue-5");
	for (int i = 0; i < 5; i++) {
	    Segment lSegment = new PercentSegment(roundDailGauge, i * 20.0, (i + 1) * 20.0);
	    roundDailGauge.segments().add(lSegment);
	}
	tachiAnchorPane.getChildren().add(roundDailGauge);
    }

    public void setData(BankSummary bm) {
	summaryListController.setData(bm);

	double maxValue = BankContainer.me().getCategories()
		.stream()
		.mapToDouble(c -> c.getBudget().getControlledAmount().doubleValue())
		.filter(d -> d > 0.0)
		.sum();
	Double total = bm.getSummary().values()
		.stream()
		.mapToDouble(o -> o.amount.doubleValue())
		.sum();
	Double budget = BankContainer.me().getCategories()
		.stream()
		.mapToDouble(c -> c.getBudget().getControlledAmount().doubleValue())
		.sum();

	roundDailGauge.setMaxValue(maxValue);
	double result = total - budget;
	if (result < 0) {
	    result = maxValue + total;
	}
	roundDailGauge.setValue(result >= 0.0 ? (result > maxValue ? maxValue : result) : 0.0);

    }
}
