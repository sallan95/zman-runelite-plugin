package com.zman;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.util.List;
import javax.inject.Inject;
import net.runelite.client.ui.overlay.OverlayPanel;
import net.runelite.client.ui.overlay.OverlayPosition;
import net.runelite.client.ui.overlay.components.LineComponent;
import net.runelite.client.ui.overlay.components.TitleComponent;

public class ZmanOverlay extends OverlayPanel
{
	private final ZmanPlugin plugin;
	private final ZmanConfig config;

	@Inject
	private ZmanOverlay(ZmanPlugin plugin, ZmanConfig config)
	{
		this.plugin = plugin;
		this.config = config;
		// TOP_RIGHT is RuneLite's managed stacking zone below the minimap — overlays
		// anchored here are vertically auto-stacked to avoid overlapping each other,
		// unlike DYNAMIC (free-floating), which was overlapping other plugins' overlays.
		setPosition(OverlayPosition.TOP_RIGHT);
	}

	@Override
	public Dimension render(Graphics2D graphics)
	{
		List<SkillRequirement> deficiencies = plugin.getDeficiencies();
		if (deficiencies.isEmpty() && !config.showAllCaughtUp())
		{
			return null;
		}

		panelComponent.getChildren().clear();
		panelComponent.getChildren().add(TitleComponent.builder()
			.text("Zman")
			.color(Color.ORANGE)
			.build());

		if (deficiencies.isEmpty())
		{
			panelComponent.getChildren().add(LineComponent.builder()
				.left("All caught up!")
				.build());
		}
		else
		{
			for (SkillRequirement requirement : deficiencies)
			{
				panelComponent.getChildren().add(LineComponent.builder()
					.left(requirement.getSkill().getName())
					.right(requirement.getActual() + " / " + requirement.getRequired())
					.build());
			}
		}

		return panelComponent.render(graphics);
	}
}
